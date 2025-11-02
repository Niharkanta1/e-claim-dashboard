import { Component, OnInit } from "@angular/core";
import { ClaimService } from "../claim.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-claim-list",
  templateUrl: "./claim-list.component.html",
  styleUrls: ["./claim-list.component.scss"],
})
export class ClaimListComponent implements OnInit {
  claims: any[] = [];

  constructor(private claimService: ClaimService, private router: Router) {}

  ngOnInit() {
    this.claimService.getAllClaims().subscribe((data) => {
      console.log("Claims: ", data);
      this.claims = data;
    });
  }

  viewClaim(claimId: number) {
    this.router.navigate(["/customer-dashboard/claim", claimId]);
  }
}
