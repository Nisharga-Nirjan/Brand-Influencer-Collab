// influencer-offer.component.ts

import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-influencer-offer',
  templateUrl: './influencer-offer.component.html',
  styleUrls: ['./influencer-offer.component.css']
})
export class InfluencerOfferComponent implements OnInit {
  user: any; // Assuming you have a user object
  requestedPosts: any[] = [];

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getUserProfile().pipe(
      tap((data) => {
        this.user = data;
        // Log user id to the console
        if (this.user && this.user.id) {
          console.log('User ID:', this.user.id);
        }
      })
    ).subscribe(
      () => {
        this.loadRequestedPosts();
      },
      (error) => {
        console.error('Error fetching user profile:', error);
      }
    );
  }

  loadRequestedPosts() {
    if (this.user && this.user.id) {
      this.userService.getInfluencerOfferStatus(this.user.id).subscribe(
        (posts) => {
          this.requestedPosts = posts;
        },
        (error) => {
          console.error('Error fetching requested posts:', error);
        }
      );
    }
  }


  getStatusColorClass(status: string): string {
    if (status === 'Accepted') {
      return 'text-green';
    } else if (status === 'Rejected') {
      return 'text-red';
    } else {
      return 'text-default';
    }
  }


  handleButtonClick(userId: number): void {
    this.router.navigate(['/profile-view', userId]);
  }

  getPaymentColorClass(paymentStatus: string): string {
    return paymentStatus.toLowerCase() === 'paid' ? 'text-green' : 'text-red';
  }



}
