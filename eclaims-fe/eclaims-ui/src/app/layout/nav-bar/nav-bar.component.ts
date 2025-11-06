import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "src/app/service/auth.service";

@Component({
  selector: "app-nav-bar",
  templateUrl: "./nav-bar.component.html",
  styleUrls: ["./nav-bar.component.scss"],
})
export class NavBarComponent implements OnInit {
  constructor(public auth: AuthService, private router: Router) {}

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
}
