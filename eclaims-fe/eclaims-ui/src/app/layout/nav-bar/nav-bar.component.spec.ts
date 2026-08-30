import { BehaviorSubject } from "rxjs";
import { NavBarComponent } from "./nav-bar.component";

describe("NavBarComponent", () => {
  function setup() {
    const userSubject = new BehaviorSubject<any>(null);
    const auth = { getCurrentUserSubject: () => userSubject, logout: jasmine.createSpy() } as any;
    const router = { navigate: jasmine.createSpy() } as any;
    const theme = { toggle: jasmine.createSpy() } as any;
    const translations = { setLanguage: jasmine.createSpy() } as any;
    return { component: new NavBarComponent(auth, router, theme, translations), auth, router, theme, translations, userSubject };
  }

  it("shows guest state until a user is published", () => {
    const test = setup();
    test.component.ngOnInit();
    expect(test.component.userLoggedIn).toBeFalse();
    test.userSubject.next({ username: "ana", role: "CUSTOMER" });
    expect(test.component.userLoggedIn).toBeTrue();
    expect(test.component.user.username).toBe("ana");
  });

  it("delegates logout, theme, and language actions", () => {
    const test = setup();
    test.component.logout();
    test.component.toggleTheme();
    test.component.setLanguage("es");
    expect(test.auth.logout).toHaveBeenCalled();
    expect(test.router.navigate).toHaveBeenCalledWith(["/login"]);
    expect(test.theme.toggle).toHaveBeenCalled();
    expect(test.translations.setLanguage).toHaveBeenCalledWith("es");
  });
});
