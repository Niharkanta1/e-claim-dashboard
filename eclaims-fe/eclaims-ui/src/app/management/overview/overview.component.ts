import { Component, OnInit } from "@angular/core";
import { ClaimService } from "src/app/service/claim.service";

@Component({
  selector: "app-overview",
  templateUrl: "./overview.component.html",
  styleUrls: ["./overview.component.scss"],
})
export class OverviewComponent implements OnInit {
  totalClaims: number = 0;
  submiited: number = 0;
  inprogress: number = 0;
  surveyCompleted: number = 0;
  approved: number = 0;
  settled: number = 0;

  constructor(private claimService: ClaimService) {}

  ngOnInit() {
    this.claimService.getAllClaims().subscribe((claims) => {
      console.log("Overview Claims:", claims);
      this.totalClaims = claims.length;
      this.submiited = 0;
      this.inprogress = 0;
      this.surveyCompleted = 0;
      this.approved = 0;
      this.settled = 0;

      // Loop through all claims and count by status
      for (const claim of claims) {
        switch (claim.status) {
          case "SUBMITTED":
            this.submiited++;
            break;
          case "IN_PROGRESS":
            this.inprogress++;
            break;
          case "SURVEY_COMPLETED":
            this.surveyCompleted++;
            break;
          case "APPROVED":
            this.approved++;
            break;
          case "SETTLED":
            this.settled++;
            break;
        }
      }
    });
  }
}
