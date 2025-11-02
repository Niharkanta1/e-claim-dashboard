import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { LoginComponent } from "./auth/login/login.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CustomerDashboardComponent } from "./dashboard/customer-dashboard/customer-dashboard.component";
import { ManagerDashboardComponent } from "./dashboard/manager-dashboard/manager-dashboard.component";
import { PartnerDashboardComponent } from "./dashboard/partner-dashboard/partner-dashboard.component";
import { NavBarComponent } from "./layout/nav-bar/nav-bar.component";
import { AboutUsComponent } from "./about-us/about-us.component";
import { ProductsComponent } from "./products/products.component";
import { SupportComponent } from "./support/support.component";
import { AdjusterDashboardComponent } from "./dashboard/adjuster-dashboard/adjsuter-dashboard.component";
import { ClaimFormComponent } from "./claim/claim-form/claim-form.component";
import { ClaimListComponent } from "./claim/claim-list/claim-list.component";
import { ClaimViewComponent } from "./claim/claim-view/claim-view.component";
import { AuthInterceptor } from "./auth/auth-jwt.interceptor";
import { ClaimProgressComponent } from './claim/claim-progress/claim-progress.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CustomerDashboardComponent,
    ManagerDashboardComponent,
    AdjusterDashboardComponent,
    PartnerDashboardComponent,
    NavBarComponent,
    AboutUsComponent,
    ProductsComponent,
    SupportComponent,
    ClaimFormComponent,
    ClaimListComponent,
    ClaimViewComponent,
    ClaimProgressComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
