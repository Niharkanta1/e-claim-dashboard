import { of } from "rxjs";
import { ClaimListComponent } from "./claim-list.component";

describe("ClaimListComponent", () => {
  function setup(role = "CUSTOMER") {
    const claims = [{ id: 1 }];
    const claimService = {
      getAllClaims: jasmine.createSpy().and.returnValue(of(claims)),
      assignUserToClaim: jasmine.createSpy().and.returnValue(of({})),
      removeUserToClaim: jasmine.createSpy().and.returnValue(of({})),
    } as any;
    const userSubject = { subscribe: (callback: Function) => callback({ role }) };
    const auth = { getCurrentUserSubject: () => userSubject } as any;
    const userService = { getUsersByRole: jasmine.createSpy().and.returnValue(of([])) } as any;
    const router = { navigate: jasmine.createSpy() } as any;
    return { component: new ClaimListComponent(claimService, router, auth, userService), claimService, userService, router };
  }

  it("loads claims and manager assignment lists", () => {
    const test = setup("MANAGER");
    test.component.ngOnInit();
    expect(test.component.claims).toEqual([{ id: 1 }]);
    expect(test.userService.getUsersByRole).toHaveBeenCalledWith("ADJUSTER");
    expect(test.userService.getUsersByRole).toHaveBeenCalledWith("SURVEYOR");
  });

  it("navigates to the dashboard for the current role", () => {
    const test = setup("PARTNER");
    test.component.ngOnInit();
    test.component.viewClaim(9);
    expect(test.router.navigate).toHaveBeenCalledWith(["/partner-dashboard/claim", 9]);
  });

  it("refreshes claims after assigning a user", () => {
    const test = setup();
    test.component.assignUser(9, 4, "ADJUSTER");
    expect(test.claimService.assignUserToClaim).toHaveBeenCalledWith(9, 4, "ADJUSTER");
    expect(test.claimService.getAllClaims).toHaveBeenCalled();
  });
});
