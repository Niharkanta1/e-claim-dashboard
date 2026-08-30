import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { AuthService } from "../../service/auth.service";
import { Router } from "@angular/router";
import { TranslationService } from "../../i18n/translation.service";

@Component({
    selector: "app-login",
    templateUrl: "./login.component.html",
    styleUrls: ["./login.component.scss"],
    standalone: false
})
export class LoginComponent implements OnInit {
  username = "";
  password = "";
  error = "";

  constructor(private auth: AuthService, private router: Router, private translations: TranslationService) {}

  ngOnInit(): void {
    this.auth.logout();
  }

  onLogin() {
    this.auth.login(this.username, this.password).subscribe({
      next: (res) => {
        console.log("Login Successful....");
        const user = this.auth.getCurrentUser();
        const role = user.role.toLowerCase();
        this.router.navigate([`/${role}-dashboard`]);
      },
      error: () => (this.error = this.translations.translate("login.invalidCredentials")),
    });
  }
}
