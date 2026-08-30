import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";
import { provideHttpClient } from "@angular/common/http";
import { TestBed } from "@angular/core/testing";
import { ClaimRequest, ClaimService } from "./claim.service";

describe("ClaimService", () => {
  let service: ClaimService;
  let http: HttpTestingController;
  const baseUrl = "http://localhost:9091/api/claims";

  beforeEach(() => {
    TestBed.configureTestingModule({ providers: [ClaimService, provideHttpClient(), provideHttpClientTesting()] });
    service = TestBed.inject(ClaimService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it("builds multipart data and omits null values when creating a claim", () => {
    const files = [new File(["one"], "one.txt"), new File(["two"], "two.txt")];
    const request = { firstName: "Ana", lastName: "Lee", policyNumber: "P1", policyType: "AUTO", dateOfIncident: "2026-01-01", claimType: "ACCIDENT", description: "Damage", contactNumber: "123", files } as ClaimRequest;
    service.createClaim(request).subscribe();

    const testRequest = http.expectOne(baseUrl);
    expect(testRequest.request.method).toBe("POST");
    const body = testRequest.request.body as FormData;
    expect(body.get("firstName")).toBe("Ana");
    expect(body.get("files")).toEqual(files[0]);
    expect(body.getAll("files")).toEqual(files);
    testRequest.flush({});
  });

  it("uses the expected claim endpoints", () => {
    service.getAllClaims().subscribe();
    const allClaimsRequest = http.expectOne({ method: "GET", url: baseUrl });
    expect(allClaimsRequest.request.method).toBe("GET");
    allClaimsRequest.flush([]);
    service.getClaimById(7).subscribe();
    const claimRequest = http.expectOne({ method: "GET", url: `${baseUrl}/7` });
    expect(claimRequest.request.method).toBe("GET");
    claimRequest.flush({});
    service.getLatestClaim().subscribe();
    const latestRequest = http.expectOne({ method: "GET", url: `${baseUrl}/-1` });
    expect(latestRequest.request.method).toBe("GET");
    latestRequest.flush({});
    service.updateClaim(7, "APPROVED").subscribe();
    http.expectOne({ method: "PUT", url: `${baseUrl}/7?status=APPROVED` }).flush({});
  });

  it("assigns and removes users with the role query", () => {
    service.assignUserToClaim(7, 3, "ADJUSTER").subscribe();
    const assignRequest = http.expectOne({ method: "PUT", url: `${baseUrl}/assign/7?userId=3&role=ADJUSTER` });
    expect(assignRequest.request.body).toEqual({});
    assignRequest.flush({});
    service.removeUserToClaim(7, "ADJUSTER").subscribe();
    const removeRequest = http.expectOne({ method: "PUT", url: `${baseUrl}/remove-assign/7?&role=ADJUSTER` });
    expect(removeRequest.request.body).toEqual({});
    removeRequest.flush({});
  });
});
