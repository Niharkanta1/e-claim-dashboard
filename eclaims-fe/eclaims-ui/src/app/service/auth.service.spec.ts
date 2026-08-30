import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";
import { provideHttpClient } from "@angular/common/http";
import { TestBed } from "@angular/core/testing";
import { AuthService } from "./auth.service";

describe("AuthService", () => {
  let service: AuthService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({ providers: [AuthService, provideHttpClient(), provideHttpClientTesting()] });
    service = TestBed.inject(AuthService);
    http = TestBed.inject(HttpTestingController);
    localStorage.clear();
  });

  afterEach(() => {
    http.verify();
    localStorage.clear();
  });

  it("posts credentials and stores the decoded user on login", () => {
    const token = `header.${btoa(JSON.stringify({ sub: "ana", roles: [{ authority: "ROLE_MANAGER" }] }))}.signature`;
    service.login("ana", "secret").subscribe();

    const request = http.expectOne("http://localhost:9091/api/auth/login");
    expect(request.request.method).toBe("POST");
    expect(request.request.body).toEqual({ username: "ana", password: "secret" });
    request.flush({ token });

    expect(service.getLocalToken()).toBe(token);
    expect(service.getCurrentUser()).toEqual({ username: "ana", role: "MANAGER", token });
  });

  it("uses an empty role when the token has no authorities", () => {
    const token = `header.${btoa(JSON.stringify({ sub: "ana", roles: [] }))}.signature`;
    service.decodeAndSetUser(token);
    expect(service.getCurrentUser()).toEqual({ username: "ana", role: "", token });
  });

  it("clears the token and publishes logout", () => {
    localStorage.setItem("token", "token");
    service.logout();
    expect(service.isLoggedIn()).toBeFalse();
    expect(service.getCurrentUser()).toBeNull();
  });
});
