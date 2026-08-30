import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../../service/auth.service";
import { TranslationService } from "../../i18n/translation.service";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss"],
  standalone: false,
})
export class RegisterComponent {
  user = { firstName: "", lastName: "", email: "", phoneNumber: "", address: "", username: "", password: "" };
  confirmPassword = "";
  error = "";
  submitted = false;

  constructor(private auth: AuthService, private router: Router, private translations: TranslationService) {}

  onRegister(): void {
    this.error = "";
    if (this.user.password !== this.confirmPassword) {
      this.error = this.translations.translate("register.passwordMismatch");
      return;
    }
    this.auth.register(this.user).subscribe({
      next: () => {
        this.submitted = true;
        setTimeout(() => this.router.navigate(["/login"]), 1200);
      },
      error: (response) => (this.error = response.error || this.translations.translate("register.failed")),
    });
  }
}
