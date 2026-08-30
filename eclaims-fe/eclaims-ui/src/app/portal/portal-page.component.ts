import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { AuthService } from "../service/auth.service";

@Component({
    selector: "app-portal-page",
    templateUrl: "./portal-page.component.html",
    styleUrls: ["./portal-page.component.scss"],
    standalone: false
})
export class PortalPageComponent implements OnInit {
  pageKey = "";
  user: any = { username: "", role: "" };
  profile = { fullName: "", email: "", phone: "", preferredContact: "Email" };
  saved = false;

  constructor(private route: ActivatedRoute, private auth: AuthService) {}

  ngOnInit(): void {
    this.pageKey = this.route.snapshot.data["page"];
    this.user = this.auth.getCurrentUser() || this.user;
    this.profile.fullName = this.user.username || "";
    this.profile.email = this.user.username ? `${this.user.username}@example.com` : "";
  }

  saveProfile(): void {
    this.saved = true;
  }
}