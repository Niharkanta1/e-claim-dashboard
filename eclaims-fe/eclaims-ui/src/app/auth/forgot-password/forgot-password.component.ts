import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../../service/auth.service";
import { TranslationService } from "../../i18n/translation.service";

@Component({
  selector: "app-forgot-password",
  templateUrl: "./forgot-password.component.html",
  styleUrls: ["./forgot-password.component.scss"],
  standalone: false,
})
export class ForgotPasswordComponent {
  username = "";
  email = "";
  newPassword = "";
  confirmPassword = "";
  error = "";
  submitted = false;

  constructor(private auth: AuthService, private router: Router, private translations: TranslationService) {}

  onResetPassword(): void {
    this.error = "";
    if (this.newPassword !== this.confirmPassword) {
      this.error = this.translations.translate("forgotPassword.passwordMismatch");
      return;
    }
    this.auth.resetPassword(this.username, this.email, this.newPassword).subscribe({
      next: () => (this.submitted = true),
      error: (response) => (this.error = response.error || this.translations.translate("forgotPassword.failed")),
    });
  }
}
