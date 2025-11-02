import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ClaimService } from "../claim.service";

@Component({
  selector: "app-claim-view",
  templateUrl: "./claim-view.component.html",
  styleUrls: ["./claim-view.component.scss"],
})
export class ClaimViewComponent implements OnInit {
  claim: any;

  constructor(
    private route: ActivatedRoute,
    private claimService: ClaimService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get("id"));
    this.claimService.getClaimById(id).subscribe((data) => {
      this.claim = data;
    });
  }
}
