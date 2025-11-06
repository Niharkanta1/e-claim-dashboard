import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { LoginComponent } from "./auth/login/login.component";
import { CustomerDashboardComponent } from "./dashboard/customer-dashboard/customer-dashboard.component";
import { ManagerDashboardComponent } from "./dashboard/manager-dashboard/manager-dashboard.component";
import { PartnerDashboardComponent } from "./dashboard/partner-dashboard/partner-dashboard.component";
import { AboutUsComponent } from "./about-us/about-us.component";
import { SupportComponent } from "./support/support.component";
import { ProductsComponent } from "./products/products.component";
import { AdjusterDashboardComponent } from "./dashboard/adjuster-dashboard/adjsuter-dashboard.component";
import { ClaimListComponent } from "./claim/claim-list/claim-list.component";
import { ClaimViewComponent } from "./claim/claim-view/claim-view.component";
import { ClaimFormComponent } from "./claim/claim-form/claim-form.component";
import { AuthGuard } from "./auth/auth.guard";
import { ClaimProgressComponent } from "./claim/claim-progress/claim-progress.component";
import { OverviewComponent } from "./management/overview/overview.component";
import { ReportsComponent } from "./management/reports/reports.component";

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  {
    path: "customer-dashboard",
    component: CustomerDashboardComponent,
    children: [
      { path: "", component: ClaimProgressComponent },
      { path: "claims", component: ClaimListComponent },
      { path: "claim/:id", component: ClaimViewComponent },
      { path: "new-claim", component: ClaimFormComponent },
    ],
    canActivate: [AuthGuard],
  },
  {
    path: "manager-dashboard",
    component: ManagerDashboardComponent,
    children: [
      { path: "", component: OverviewComponent },
      { path: "claims", component: ClaimListComponent },
      { path: "claim/:id", component: ClaimViewComponent },
      { path: "reports", component: ReportsComponent },
    ],
    canActivate: [AuthGuard],
  },
  {
    path: "adjuster-dashboard",
    component: AdjusterDashboardComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "partner-dashboard",
    component: PartnerDashboardComponent,
    canActivate: [AuthGuard],
  },
  { path: "aboutus", component: AboutUsComponent },
  { path: "support", component: SupportComponent },
  { path: "products", component: ProductsComponent },
  { path: "**", redirectTo: "login" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
