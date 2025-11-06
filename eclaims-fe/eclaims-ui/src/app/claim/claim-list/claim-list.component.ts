import { Component, OnInit } from "@angular/core";
import { ClaimService } from "../../service/claim.service";
import { Router } from "@angular/router";
import { AuthService } from "src/app/service/auth.service";
import { UserService } from "src/app/service/user.service";

@Component({
  selector: "app-claim-list",
  templateUrl: "./claim-list.component.html",
  styleUrls: ["./claim-list.component.scss"],
})
export class ClaimListComponent implements OnInit {
  claims: any[] = [];
  role: string = "";

  adjusters: any[] = [];
  surveyors: any[] = [];

  constructor(
    private claimService: ClaimService,
    private router: Router,
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.authService.getCurrentUserSubject().subscribe((user) => {
      if (user) {
        this.role = user.role;
        if (this.role === "MANAGER") {
          this.userService.getUsersByRole("ADJUSTER").subscribe((data) => {
            this.adjusters = data;
            console.log("Adjusters: ", this.adjusters);
          });
          this.userService.getUsersByRole("SURVEYOR").subscribe((data) => {
            this.surveyors = data;
            console.log("Surveyors: ", this.surveyors);
          });
        }
      }
    });
    console.log("User Role: ", this.role);
    this.fetchClaim();
  }

  private fetchClaim() {
    this.claimService.getAllClaims().subscribe((data) => {
      console.log("Claims: ", data);
      this.claims = data;
    });
  }

  viewClaim(claimId: number) {
    this.router.navigate(["/customer-dashboard/claim", claimId]);
  }

  assignUser(claimId: number, value: any, role: string) {
    console.log("Assigning Adjuster: ", value, " to Claim ID: ", claimId);
    this.claimService.assignUserToClaim(claimId, value, role).subscribe({
      next: (res) => {
        console.log("Adjuster assigned successfully.");
        this.fetchClaim();
      },
      error: (err) => {
        console.error("Error assigning adjuster: ", err);
      },
    });
  }

  removeAssignment(claimId: number, role: string) {
    console.log("Removing ", role, " from Claim ID: ", claimId);
    this.claimService.removeUserToClaim(claimId, role).subscribe({
      next: (res) => {
        console.log(role, "User removed successfully.");
        this.fetchClaim();
      },
      error: (err) => {
        console.error("Error removing assignment: ", err);
      },
    });
  }
}
