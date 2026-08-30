import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import enLocale from "./locales/en.json";
import esLocale from "./locales/es.json";
import itLocale from "./locales/it.json";
import zhLocale from "./locales/zh.json";

export type Language = "en" | "es" | "it" | "zh";
type LocaleDictionary = Record<string, string>;

export const LANGUAGE_OPTIONS: { code: Language; label: string }[] = [
  { code: "en", label: "English" },
  { code: "es", label: "Español" },
  { code: "it", label: "Italiano" },
  { code: "zh", label: "中文" },
];

@Injectable({ providedIn: "root" })
export class TranslationService {
  private readonly storageKey = "eclaims-language";
  private readonly locales: Record<Language, LocaleDictionary> = {
    en: enLocale,
    es: { ...enLocale, ...esLocale },
    it: { ...enLocale, ...itLocale },
    zh: { ...enLocale, ...zhLocale },
  };
  private readonly languageSubject = new BehaviorSubject<Language>(this.readLanguage());
  readonly language$ = this.languageSubject.asObservable();

  constructor() {
    document.documentElement.lang = this.language;
  }

  get language(): Language {
    return this.languageSubject.value;
  }

  setLanguage(language: Language): void {
    this.languageSubject.next(language);
    localStorage.setItem(this.storageKey, language);
    document.documentElement.lang = language;
  }

  translate(key: string): string {
    return this.locales[this.language][key] || this.locales.en[key] || key;
  }

  private readLanguage(): Language {
    const saved = localStorage.getItem(this.storageKey) as Language;
    return ["en", "es", "it", "zh"].includes(saved) ? saved : "en";
  }
}
