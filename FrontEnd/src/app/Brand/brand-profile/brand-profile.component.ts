import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router'; // Import the Router

@Component({
  selector: 'app-brand-profile',
  templateUrl: './brand-profile.component.html',
  styleUrls: ['./brand-profile.component.css'],
})
export class BrandProfileComponent implements OnInit {
  user: any;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getUserProfile().subscribe(
      (data) => {
        this.user = data;
      },
      (error) => {
        console.error('Error fetching user profile:', error);
      }
    );
  }

  onEditProfileClick() {
    // Use the user's ID or any unique identifier to create a dynamic route
    // For example, assuming user.id is the unique identifier
    if (this.user && this.user.id) {
      this.router.navigate(['/brand-profile-edit', this.user.id]);
    } else {
      console.error('User ID not found.');
    }
  }
}
