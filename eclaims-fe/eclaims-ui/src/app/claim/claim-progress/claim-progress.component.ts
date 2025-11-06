import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { ClaimService } from "../../service/claim.service";

@Component({
  selector: "app-claim-progress",
  templateUrl: "./claim-progress.component.html",
  styleUrls: ["./claim-progress.component.scss"],
})
export class ClaimProgressComponent implements OnInit {
  claim: any;

  constructor(
    private route: ActivatedRoute,
    private claimService: ClaimService,
    private router: Router
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get("id"));
    this.claimService.getLatestClaim().subscribe((data) => {
      this.claim = data;
      console.log("Claim: ", this.claim);
    });
  }

  showClaimDetails() {
    this.router.navigate(["/customer-dashboard/claim", this.claim.claimId]);
  }
}
