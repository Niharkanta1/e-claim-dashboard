import { HttpRequest, HttpResponse } from "@angular/common/http";
import { AuthInterceptor } from "./auth-jwt.interceptor";

describe("AuthInterceptor", () => {
  it("adds a bearer token when one exists", () => {
    const auth = { getLocalToken: () => "abc" } as any;
    const next = { handle: jasmine.createSpy("handle").and.returnValue(new ObservableStub()) } as any;
    new AuthInterceptor(auth).intercept(new HttpRequest("GET", "/claims"), next).subscribe();
    expect(next.handle).toHaveBeenCalledWith(jasmine.objectContaining({ headers: jasmine.anything() }));
    expect(next.handle.calls.mostRecent().args[0].headers.get("Authorization")).toBe("Bearer abc");
  });

  it("forwards the original request without a token", () => {
    const auth = { getLocalToken: () => null } as any;
    const next = { handle: jasmine.createSpy("handle").and.returnValue(new ObservableStub()) } as any;
    const request = new HttpRequest("GET", "/claims");
    new AuthInterceptor(auth).intercept(request, next).subscribe();
    expect(next.handle).toHaveBeenCalledWith(request);
  });
});

class ObservableStub {
  subscribe(): void {}
}
