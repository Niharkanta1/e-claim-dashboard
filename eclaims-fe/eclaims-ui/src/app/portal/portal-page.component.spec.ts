import { PortalPageComponent } from "./portal-page.component";

describe("PortalPageComponent", () => {
  it("loads page data and derives profile fields from the user", () => {
    const route = { snapshot: { data: { page: "claims" } } } as any;
    const auth = { getCurrentUser: () => ({ username: "ana", role: "CUSTOMER" }) } as any;
    const component = new PortalPageComponent(route, auth);
    component.ngOnInit();
    expect(component.pageKey).toBe("claims");
    expect(component.profile.fullName).toBe("ana");
    expect(component.profile.email).toBe("ana@example.com");
  });

  it("marks the profile as saved", () => {
    const component = new PortalPageComponent({ snapshot: { data: {} } } as any, { getCurrentUser: () => null } as any);
    component.saveProfile();
    expect(component.saved).toBeTrue();
  });
});
