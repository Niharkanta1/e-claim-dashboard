import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { LoginComponent } from "./auth/login/login.component";
import { CustomerDashboardComponent } from "./dashboard/customer-dashboard/customer-dashboard.component";
import { ManagerDashboardComponent } from "./dashboard/manager-dashboard/manager-dashboard.component";
import { SurveyorDashboardComponent } from "./dashboard/surveyor-dashboard/surveyor-dashboard.component";
import { PartnerDashboardComponent } from "./dashboard/partner-dashboard/partner-dashboard.component";
import { AboutUsComponent } from "./about-us/about-us.component";
import { SupportComponent } from "./support/support.component";
import { ProductsComponent } from "./products/products.component";

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  { path: "customer-dashboard", component: CustomerDashboardComponent },
  { path: "manager-dashboard", component: ManagerDashboardComponent },
  { path: "surveyor-dashboard", component: SurveyorDashboardComponent },
  { path: "partner-dashboard", component: PartnerDashboardComponent },
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
