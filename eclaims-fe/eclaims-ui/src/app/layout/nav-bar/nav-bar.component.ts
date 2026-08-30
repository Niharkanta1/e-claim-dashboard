import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "src/app/service/auth.service";
import { ThemeService } from "src/app/service/theme.service";
import { LANGUAGE_OPTIONS, Language, TranslationService } from "src/app/i18n/translation.service";

@Component({
    selector: "app-nav-bar",
    templateUrl: "./nav-bar.component.html",
    styleUrls: ["./nav-bar.component.scss"],
    standalone: false
})
export class NavBarComponent implements OnInit {
  constructor(public auth: AuthService, private router: Router, public theme: ThemeService, public translations: TranslationService) {}

  readonly languages = LANGUAGE_OPTIONS;

  userLoggedIn: boolean = false;
  user: any;

  ngOnInit(): void {
    this.auth.getCurrentUserSubject().subscribe((user) => {
      if (user && user.username) {
        this.userLoggedIn = true;
        this.user = user;
      } else {
        this.userLoggedIn = false;
        this.user = { username: "Guest", role: null, token: null };
      }
    });
    this.user = { username: "Guest", role: null, token: null };
  }

  logout() {
    this.auth.logout();
    this.router.navigate(["/login"]);
  }

  login() {
    this.auth.logout();
    this.router.navigate(["/login"]);
  }

  register() {
    this.router.navigate(["/register"]);
  }

  changePassword() {
    this.router.navigate(["/change-password"]);
  }

  toggleTheme(): void {
    this.theme.toggle();
  }

  setLanguage(language: Language): void {
    this.translations.setLanguage(language);
  }
}
