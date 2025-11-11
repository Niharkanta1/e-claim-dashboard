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
  assignedToAdjusterDate: Date | null = null;
  assignedToSurveyorDate: Date | null = null;
  surveyCompletedDate: Date | null = null;
  approvedDate: Date | null = null;
  settledDate: Date | null = null;

  constructor(
    private route: ActivatedRoute,
    private claimService: ClaimService,
    private router: Router
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get("id"));
    this.claimService.getLatestClaim().subscribe((data) => {
      this.claim = data;
      this.assignedToAdjusterDate = this.getEventDate("ADJUSTER_ASSIGNED");
      this.assignedToSurveyorDate = this.getEventDate("SURVEYOR_ASSIGNED");
      this.surveyCompletedDate = this.getEventDate("SURVEY_COMPLETED");
      this.approvedDate = this.getEventDate("APPROVED");
      this.settledDate = this.getEventDate("SETTLED");
      console.log("Claim: ", this.claim);
    });
  }

  showClaimDetails() {
    this.router.navigate(["/customer-dashboard/claim", this.claim.claimId]);
  }

  getEventDate(eventType: string): Date | null {
    if (!this.claim || !this.claim.events) {
      return null;
    }
    const event = this.claim.events.find((e: any) => e.event === eventType);
    return event && event.eventTime ? new Date(event.eventTime) : null;
  }
}
