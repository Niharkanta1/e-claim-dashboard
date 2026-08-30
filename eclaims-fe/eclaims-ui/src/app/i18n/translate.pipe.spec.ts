import { TranslatePipe } from "./translate.pipe";

describe("TranslatePipe", () => {
  it("delegates translation to the service", () => {
    const translations = { translate: jasmine.createSpy().and.returnValue("Claim") } as any;
    const pipe = new TranslatePipe(translations);
    expect(pipe.transform("claim.title")).toBe("Claim");
    expect(translations.translate).toHaveBeenCalledWith("claim.title");
  });
});
