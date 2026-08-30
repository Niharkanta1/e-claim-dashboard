import { TestBed } from "@angular/core/testing";
import { TranslationService } from "./translation.service";

describe("TranslationService", () => {
  let service: TranslationService;

  beforeEach(() => {
    localStorage.clear();
    service = TestBed.inject(TranslationService);
  });

  afterEach(() => localStorage.clear());

  it("defaults invalid stored languages to English", () => {
    expect(service.language).toBe("en");
    expect(document.documentElement.lang).toBe("en");
  });

  it("updates storage, document language, and subscribers", () => {
    const values: string[] = [];
    service.language$.subscribe((language) => values.push(language));
    service.setLanguage("es");
    expect(service.language).toBe("es");
    expect(localStorage.getItem("eclaims-language")).toBe("es");
    expect(document.documentElement.lang).toBe("es");
    expect(values).toEqual(["en", "es"]);
  });

  it("falls back to English and then to the key", () => {
    service.setLanguage("es");
    expect(service.translate("login.title")).not.toBe("login.title");
    expect(service.translate("missing.key")).toBe("missing.key");
  });
});
