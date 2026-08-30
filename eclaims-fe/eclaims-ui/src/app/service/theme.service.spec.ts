import { DOCUMENT } from "@angular/common";
import { TestBed } from "@angular/core/testing";
import { ThemeService } from "./theme.service";

describe("ThemeService", () => {
  beforeEach(() => {
    localStorage.clear();
    document.body.classList.remove("theme-night");
    spyOn(window, "matchMedia").and.returnValue({ matches: false } as MediaQueryList);
  });

  afterEach(() => localStorage.clear());

  function createService(): ThemeService {
    TestBed.configureTestingModule({ providers: [ThemeService, { provide: DOCUMENT, useValue: document }] });
    return TestBed.inject(ThemeService);
  }

  it("uses a stored theme and persists toggles", () => {
    localStorage.setItem("eclaims-theme", "night");
    const service = createService();
    expect(service.isNight).toBeTrue();
    expect(document.body.classList.contains("theme-night")).toBeTrue();
    service.toggle();
    expect(service.isNight).toBeFalse();
    expect(localStorage.getItem("eclaims-theme")).toBe("day");
  });

  it("falls back to the system preference for an invalid stored value", () => {
    (window.matchMedia as jasmine.Spy).and.returnValue({ matches: true } as MediaQueryList);
    localStorage.setItem("eclaims-theme", "unknown");
    expect(createService().isNight).toBeTrue();
  });
});
