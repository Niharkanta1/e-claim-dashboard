import { Pipe, PipeTransform } from "@angular/core";
import { TranslationService } from "./translation.service";

@Pipe({ name: "translate", pure: false, standalone: false })
export class TranslatePipe implements PipeTransform {
  constructor(private translations: TranslationService) {}
  transform(key: string): string { return this.translations.translate(key); }
}