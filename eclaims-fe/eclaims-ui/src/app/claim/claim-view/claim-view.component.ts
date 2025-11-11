import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ClaimService } from "../../service/claim.service";
import { AuthService } from "src/app/service/auth.service";

@Component({
  selector: "app-claim-view",
  templateUrl: "./claim-view.component.html",
  styleUrls: ["./claim-view.component.scss"],
})
export class ClaimViewComponent implements OnInit {
  claim: any;
  role: string = "";
  customerDocuments: string[] = [];

  constructor(
    private route: ActivatedRoute,
    private claimService: ClaimService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get("id"));
    this.claimService.getClaimById(id).subscribe((data) => {
      this.claim = data;
      console.log("Claim Details:", this.claim);
    });
    this.authService.getCurrentUserSubject().subscribe((user) => {
      if (user) {
        this.role = user.role;
        console.log("User Role:", this.role);
      }
    });
    this.customerDocuments.push("assets/documents/document1.pdf");
    this.customerDocuments.push("assets/documents/document2.pdf");
    this.customerDocuments.push("assets/images/image1.jpeg");
    this.customerDocuments.push("assets/images/image2.jpeg");
    this.customerDocuments.push("assets/images/image3.jpeg");
  }

  confirmAction(action: "approve" | "settle" | "reject") {
    let message = "";
    switch (action) {
      case "approve":
        message = "Are you sure you want to approve this claim?";
        break;
      case "settle":
        message = "Are you sure you want to settle this claim?";
        break;
      case "reject":
        message = "Are you sure you want to reject this claim?";
        break;
    }

    if (confirm(message)) {
      if (action === "approve") this.approveClaim();
      else if (action === "settle") this.settleClaim();
      else if (action === "reject") this.rejectClaim();
    }
  }

  approveClaim() {
    if (this.role !== "PARTNER" && this.role !== "ADJUSTER") return;
    this.claimService
      .updateClaim(
        this.claim.claimId,
        this.role === "ADJUSTER" ? "APPROVED" : "SURVEY_COMPLETED"
      )
      .subscribe((data) => {
        this.claim = data;
      });
  }

  rejectClaim() {
    this.claimService
      .updateClaim(this.claim.claimId, "REJECTED")
      .subscribe((data) => {
        this.claim = data;
      });
  }

  settleClaim() {
    this.claimService
      .updateClaim(this.claim.claimId, "SETTLED")
      .subscribe((data) => {
        this.claim = data;
      });
  }

  canRejectable(): boolean {
    return !(
      this.role === "CUSTOMER" ||
      this.claim.status === "REJECTED" ||
      this.claim.status === "SETTLED" ||
      (this.claim.status === "SURVEY_COMPLETED" && this.role === "PARTNER") ||
      (this.claim.status === "APPROVED" && this.role === "ADJUSTER") ||
      (this.claim.status === "SETTLED" && this.role === "MANAGER")
    );
  }
}
