// app.module.ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserService } from './services/user.service';
import { BrandProfileComponent } from './Brand/brand-profile/brand-profile.component';
import { BrandHomeComponent } from './Brand/brand-home/brand-home.component';
import { BrandCollabComponent } from './Brand/brand-collab/brand-collab.component';
import { BrandPaymentComponent } from './Brand/brand-payment/brand-payment.component';
import { InfluencerProfileComponent } from './Influencer/influencer-profile/influencer-profile.component';
import { InfluencerHomeComponent } from './Influencer/influencer-home/influencer-home.component';
import { InfluencerCollabComponent } from './Influencer/influencer-collab/influencer-collab.component';
import { InfluencerPaymentComponent } from './Influencer/influencer-payment/influencer-payment.component';
import { AppLoginComponent } from './app-login/app-login.component';
import { NavigationComponent } from './navigation/navigation.component';
import { AppRegistrationComponent } from './app-registration/app-registration.component';
import { ProfileViewComponent } from './profile-view/profile-view.component';
import { BrandProfileEditComponent } from './Brand/brand-profile-edit/brand-profile-edit.component';
import { InfluencerProfileEditComponent } from './Influencer/influencer-profile-edit/influencer-profile-edit.component';
import { BrandPostComponent } from './Brand/brand-post/brand-post.component';
import { InfluencerOfferComponent } from './Influencer/influencer-offer/influencer-offer.component';
import { AdminHomeComponent } from './Admin/admin-home/admin-home.component';


@NgModule({
  declarations: [
    AppComponent, // Add this line
    BrandProfileComponent,
    BrandHomeComponent,
    BrandCollabComponent,
    BrandPaymentComponent,
    InfluencerProfileComponent,
    InfluencerHomeComponent,
    InfluencerCollabComponent,
    InfluencerPaymentComponent,
    AppLoginComponent,
    NavigationComponent,
    AppRegistrationComponent,
    BrandProfileEditComponent,
    InfluencerProfileEditComponent,
    BrandPostComponent,
    InfluencerOfferComponent,
    ProfileViewComponent,
    AdminHomeComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
