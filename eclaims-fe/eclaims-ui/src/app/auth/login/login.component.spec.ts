import { Router } from "@angular/router";
import { of, throwError } from "rxjs";
import { LoginComponent } from "./login.component";

describe("LoginComponent", () => {
  it("logs out when initialized", () => {
    const auth = { logout: jasmine.createSpy(), login: jasmine.createSpy(), getCurrentUser: jasmine.createSpy() } as any;
    const component = new LoginComponent(auth, {} as Router, {} as any);
    component.ngOnInit();
    expect(auth.logout).toHaveBeenCalled();
  });

  it("passes credentials and navigates to the role dashboard", () => {
    const auth = { login: jasmine.createSpy().and.returnValue(of({})), getCurrentUser: () => ({ role: "MANAGER" }) } as any;
    const router = { navigate: jasmine.createSpy() } as any;
    const component = new LoginComponent(auth, router, {} as any);
    component.username = "ana";
    component.password = "secret";
    component.onLogin();
    expect(auth.login).toHaveBeenCalledWith("ana", "secret");
    expect(router.navigate).toHaveBeenCalledWith(["/manager-dashboard"]);
  });

  it("translates authentication failures", () => {
    const auth = { login: jasmine.createSpy().and.returnValue(throwError(() => new Error("invalid"))) } as any;
    const translations = { translate: jasmine.createSpy().and.returnValue("Invalid credentials") } as any;
    const component = new LoginComponent(auth, {} as Router, translations);
    component.onLogin();
    expect(translations.translate).toHaveBeenCalledWith("login.invalidCredentials");
    expect(component.error).toBe("Invalid credentials");
  });
});
