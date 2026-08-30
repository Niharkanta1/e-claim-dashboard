import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../../service/auth.service";
import { TranslationService } from "../../i18n/translation.service";

@Component({
  selector: "app-change-password",
  templateUrl: "./change-password.component.html",
  styleUrls: ["./change-password.component.scss"],
  standalone: false,
})
export class ChangePasswordComponent {
  currentPassword = "";
  newPassword = "";
  confirmPassword = "";
  error = "";
  submitted = false;

  constructor(private auth: AuthService, private router: Router, private translations: TranslationService) {}

  onChangePassword(): void {
    this.error = "";
    if (this.newPassword !== this.confirmPassword) {
      this.error = this.translations.translate("changePassword.passwordMismatch");
      return;
    }
    this.auth.changePassword(this.currentPassword, this.newPassword).subscribe({
      next: () => (this.submitted = true),
      error: (response) => (this.error = response.error || this.translations.translate("changePassword.failed")),
    });
  }

  returnToDashboard(): void {
    const role = this.auth.getCurrentUser()?.role?.toLowerCase();
    this.router.navigate([role ? `/${role}-dashboard` : "/login"]);
  }
}
