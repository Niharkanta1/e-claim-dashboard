import { of } from "rxjs";
import { OverviewComponent } from "./overview.component";

describe("OverviewComponent", () => {
  it("counts recognized claim statuses and ignores unknown ones", () => {
    const service = { getAllClaims: () => of([{ status: "SUBMITTED" }, { status: "IN_PROGRESS" }, { status: "SURVEY_COMPLETED" }, { status: "APPROVED" }, { status: "SETTLED" }, { status: "OTHER" }]) };
    const component = new OverviewComponent(service as any);
    component.ngOnInit();
    expect(component.totalClaims).toBe(6);
    expect(component.submiited).toBe(1);
    expect(component.inprogress).toBe(1);
    expect(component.surveyCompleted).toBe(1);
    expect(component.approved).toBe(1);
    expect(component.settled).toBe(1);
  });

  it("resets status counts when claims are reloaded", () => {
    const service = { getAllClaims: jasmine.createSpy().and.returnValue(of([{ status: "SETTLED" }])) };
    const component = new OverviewComponent(service as any);
    component.submiited = 4;
    component.ngOnInit();
    expect(component.submiited).toBe(0);
    expect(component.settled).toBe(1);
  });
});
