import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { AuthService } from "../auth.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  username = "";
  password = "";
  error = "";

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {}

  onLogin() {
    this.auth.login(this.username, this.password).subscribe({
      next: (res) => {
        console.log("Login Successful....");
        const user = this.auth.getCurrentUser();
        const role = user.role.toLowerCase();
        this.router.navigate([`/${role}-dashboard`]);
      },
      error: () => (this.error = "Invalid credentials"),
    });
  }
}
