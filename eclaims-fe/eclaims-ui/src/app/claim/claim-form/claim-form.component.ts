import { Component, OnInit } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { ClaimService } from "../../service/claim.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-claim-form",
  templateUrl: "./claim-form.component.html",
  styleUrls: ["./claim-form.component.scss"],
})
export class ClaimFormComponent implements OnInit {
  files: File[] = [];
  submitting: boolean = false;

  claimForm = this.fb.group({
    firstName: ["", Validators.required],
    lastName: ["", Validators.required],
    policyNumber: ["", Validators.required],
    policyType: ["", Validators.required],
    dateOfIncident: ["", Validators.required],
    claimType: ["", Validators.required],
    description: ["", Validators.required],
    contactNumber: ["", Validators.required],
  });

  constructor(
    private fb: FormBuilder,
    private claimService: ClaimService,
    private router: Router
  ) {}

  ngOnInit() {}

  onFilesChange(event: any) {
    this.files = Array.from(event.target.files);
  }

  submitClaim() {
    if (this.claimForm.invalid) return;
    this.submitting = true;

    const data = { ...this.claimForm.value, files: this.files };
    this.claimService.createClaim(data).subscribe({
      next: () => {
        alert("Claim submitted succcessfully!");
        this.claimForm.reset();
        this.files = [];
        this.submitting = false;
        this.router.navigate(["/customer-dashboard/claims"]);
      },
      error: (err) => {
        console.log(err);
        this.submitting = false;
      },
    });
  }
}
