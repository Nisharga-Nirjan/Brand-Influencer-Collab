// app-routing.module.ts

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrandProfileComponent } from './Brand/brand-profile/brand-profile.component';
import { BrandHomeComponent } from './Brand/brand-home/brand-home.component';
import { BrandCollabComponent } from './Brand/brand-collab/brand-collab.component';
import { BrandPaymentComponent } from './Brand/brand-payment/brand-payment.component';
import { InfluencerProfileComponent } from './Influencer/influencer-profile/influencer-profile.component';
import { InfluencerHomeComponent } from './Influencer/influencer-home/influencer-home.component';
import { InfluencerCollabComponent } from './Influencer/influencer-collab/influencer-collab.component';
import { InfluencerPaymentComponent } from './Influencer/influencer-payment/influencer-payment.component';
import { AppLoginComponent } from './app-login/app-login.component';
import { AppRegistrationComponent } from './app-registration/app-registration.component'; // Import the new component
import { BrandProfileEditComponent } from './Brand/brand-profile-edit/brand-profile-edit.component';
import { InfluencerProfileEditComponent } from './Influencer/influencer-profile-edit/influencer-profile-edit.component';
import { BrandPostComponent } from './Brand/brand-post/brand-post.component';
import { InfluencerOfferComponent } from './Influencer/influencer-offer/influencer-offer.component';
import { ProfileViewComponent } from './profile-view/profile-view.component';
import { AdminHomeComponent } from './Admin/admin-home/admin-home.component';

const routes: Routes = [
  { path: 'brand-profile', component: BrandProfileComponent },
  { path: 'brand-home', component: BrandHomeComponent },
  { path: 'brand-collab', component: BrandCollabComponent },
  { path: 'brand-payment/:id', component: BrandPaymentComponent },
  { path: 'influencer-profile', component: InfluencerProfileComponent },
  { path: 'influencer-home', component: InfluencerHomeComponent },
  { path: 'influencer-collab', component: InfluencerCollabComponent },
  { path: 'influencer-payment', component: InfluencerPaymentComponent },
  { path: 'app-login', component: AppLoginComponent },
  { path: 'app-registration', component: AppRegistrationComponent },
  { path: 'registration', component: AppRegistrationComponent },
  { path: 'brand-profile-edit/:id', component: BrandProfileEditComponent },
  { path: 'influencer-profile-edit/:id', component: InfluencerProfileEditComponent },
  { path: 'brand-post', component: BrandPostComponent },
  { path: 'influencer-offer', component: InfluencerOfferComponent },
  { path: 'profile-view/:userId', component: ProfileViewComponent },
  { path: 'admin-home', component: AdminHomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
