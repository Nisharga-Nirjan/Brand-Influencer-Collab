import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Subscription } from 'rxjs';



@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit, OnDestroy {

  isLoggedIn: boolean = false;
  userName: string | null = null;
  userRole: string | null = null;
  private roleChangeSubscription: Subscription;

  constructor(private router: Router, private userService: UserService, private cdr: ChangeDetectorRef) {
    this.roleChangeSubscription = this.userService.roleChanged.subscribe(() => {
      console.log('Role changed detected in NavigationComponent');
      this.checkLoggedIn();
    });
  }

  ngOnInit() {
    this.checkLoggedIn();
  }

  ngOnDestroy() {
    this.roleChangeSubscription.unsubscribe();
  }

  checkLoggedIn() {
    const storedToken = localStorage.getItem('token');
    if (storedToken) {
      const decodedToken = this.userService.getDecodedToken();
      if (decodedToken) {
        this.isLoggedIn = true;
        this.userRole = decodedToken.role;
        this.userName = decodedToken.name;
        this.navigateBasedOnRole();
      }
    } else {
      this.isLoggedIn = false;
      this.userName = null;
      this.userRole = null;
    }
  }

  logout() {
    localStorage.removeItem('token');
    this.isLoggedIn = false;
    this.userName = null;
    this.userRole = null;
    this.router.navigate(['/app-login']);
  }

  navigateToLogin() {
    this.router.navigate(['/app-login']);
  }

  navigateToRegistration() {
    this.router.navigate(['/app-registration']);
  }

  // Use the userRole to navigate based on role
  navigateBasedOnRole() {
    switch (this.userRole) {
      case 'brand':
        this.router.navigate(['/brand-home']);
        break;
      case 'influencer':
        this.router.navigate(['/influencer-home']);
        break;
        case 'admin':
          this.router.navigate(['/admin-home']);
          break;
      default:
        console.error('Unknown user role:', this.userRole);
      // Add more conditions as needed
    }
  }
  getUserProfileLink(): string {
    switch (this.userRole) {
      case 'brand':
        return '/brand-profile';
      case 'influencer':
        return '/influencer-profile';
        case 'admin':
        return '/admin-home';
      default:
        console.error('Unknown user role:', this.userRole);
        // You can either return a default link or handle this case according to your requirements
        return '/default-profile';
    }
  }

  getButtonLabel(): string {
    if (this.userRole === 'brand') {
      return 'Posts';
    } else if (this.userRole === 'influencer') {
      return 'Offers';
    } else {
      return ''; // For 'admin', show nothing
    }
  }

navigateToBrandPost() {
    this.router.navigate(['/brand-post']); // Replace 'brand-posts' with the actual route for the brand post page
}

navigateToInfluencerOffer() {
    this.router.navigate(['/influencer-offer']); // Replace 'influencer-offers' with the actual route for the influencer offer page
}
}
