import { FormBuilder } from "@angular/forms";
import { Router } from "@angular/router";
import { of, throwError } from "rxjs";
import { ClaimFormComponent } from "./claim-form.component";

describe("ClaimFormComponent", () => {
  function createComponent(service: any = {}): ClaimFormComponent {
    return new ClaimFormComponent(new FormBuilder(), service, { navigate: jasmine.createSpy() } as any);
  }

  it("starts with required fields invalid", () => {
    expect(createComponent().claimForm.invalid).toBeTrue();
  });

  it("does not submit an invalid form", () => {
    const service = { createClaim: jasmine.createSpy() };
    createComponent(service).submitClaim();
    expect(service.createClaim).not.toHaveBeenCalled();
  });

  it("submits valid values and navigates after success", () => {
    const service = { createClaim: jasmine.createSpy().and.returnValue(of({})) };
    const router = { navigate: jasmine.createSpy() } as any;
    const component = new ClaimFormComponent(new FormBuilder(), service as any, router);
    spyOn(window, "alert");
    component.claimForm.setValue({ firstName: "Ana", lastName: "Lee", policyNumber: "P1", policyType: "AUTO", dateOfIncident: "2026-01-01", claimType: "ACCIDENT", description: "Damage", contactNumber: "123" });
    component.files = [new File(["x"], "x.txt")];
    const submittedFiles = component.files;
    component.submitClaim();
    expect(service.createClaim).toHaveBeenCalledWith(jasmine.objectContaining({ firstName: "Ana", files: submittedFiles }));
    expect(component.submitting).toBeFalse();
    expect(component.files).toEqual([]);
    expect(router.navigate).toHaveBeenCalledWith(["/customer-dashboard/claims"]);
  });

  it("clears the submitting state after a failed request", () => {
    const component = createComponent({ createClaim: () => throwError(() => new Error("offline")) });
    component.claimForm.setValue({ firstName: "Ana", lastName: "Lee", policyNumber: "P1", policyType: "AUTO", dateOfIncident: "2026-01-01", claimType: "ACCIDENT", description: "Damage", contactNumber: "123" });
    component.submitClaim();
    expect(component.submitting).toBeFalse();
  });
});
