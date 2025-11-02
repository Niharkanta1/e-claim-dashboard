import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";

@Component({
  selector: "app-customer-dashboard",
  templateUrl: "./customer-dashboard.component.html",
  styleUrls: ["./customer-dashboard.component.scss"],
})
export class CustomerDashboardComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit() {}

  backToDashboard() {
    this.router.navigate(["/customer-dashboard"]);
  }
}
