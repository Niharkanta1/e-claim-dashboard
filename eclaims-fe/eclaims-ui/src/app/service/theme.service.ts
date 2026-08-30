import { DOCUMENT } from "@angular/common";
import { Inject, Injectable } from "@angular/core";

export type ThemeMode = "day" | "night";

@Injectable({ providedIn: "root" })
export class ThemeService {
  private readonly storageKey = "eclaims-theme";
  private mode: ThemeMode = "day";

  constructor(@Inject(DOCUMENT) private document: Document) {
    const storedMode = this.readStoredMode();
    this.setMode(storedMode || this.getPreferredMode());
  }

  get isNight(): boolean {
    return this.mode === "night";
  }

  toggle(): void {
    this.setMode(this.isNight ? "day" : "night");
  }

  private setMode(mode: ThemeMode): void {
    this.mode = mode;
    this.document.body.classList.toggle("theme-night", this.isNight);

    try {
      localStorage.setItem(this.storageKey, mode);
    } catch {
      // Storage can be unavailable in privacy-restricted browser contexts.
    }
  }

  private readStoredMode(): ThemeMode | null {
    try {
      const storedMode = localStorage.getItem(this.storageKey);
      return storedMode === "day" || storedMode === "night" ? storedMode : null;
    } catch {
      return null;
    }
  }

  private getPreferredMode(): ThemeMode {
    return window.matchMedia?.("(prefers-color-scheme: dark)").matches ? "night" : "day";
  }
}