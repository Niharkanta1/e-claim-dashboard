import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { FormsModule } from '@angular/forms';
import { CustomerDashboardComponent } from './dashboard/customer-dashboard/customer-dashboard.component';
import { ManagerDashboardComponent } from './dashboard/manager-dashboard/manager-dashboard.component';
import { SurveyorDashboardComponent } from './dashboard/surveyor-dashboard/surveyor-dashboard.component';
import { PartnerDashboardComponent } from './dashboard/partner-dashboard/partner-dashboard.component';
import { NavBarComponent } from './layout/nav-bar/nav-bar.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { ProductsComponent } from './products/products.component';
import { SupportComponent } from './support/support.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CustomerDashboardComponent,
    ManagerDashboardComponent,
    SurveyorDashboardComponent,
    PartnerDashboardComponent,
    NavBarComponent,
    AboutUsComponent,
    ProductsComponent,
    SupportComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
