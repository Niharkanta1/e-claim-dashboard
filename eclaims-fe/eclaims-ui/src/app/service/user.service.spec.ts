import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";
import { provideHttpClient } from "@angular/common/http";
import { TestBed } from "@angular/core/testing";
import { UserService } from "./user.service";

describe("UserService", () => {
  let service: UserService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({ providers: [UserService, provideHttpClient(), provideHttpClientTesting()] });
    service = TestBed.inject(UserService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it("loads users by role", () => {
    service.getUsersByRole("ADJUSTER").subscribe();
    const request = http.expectOne("http://localhost:9091/api/users?userRole=ADJUSTER");
    expect(request.request.method).toBe("GET");
    request.flush([]);
  });
});
