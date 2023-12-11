import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

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
    if (this.user && this.user.id) {
      this.router.navigate(['/brand-profile-edit', this.user.id]);
    } else {
      console.error('User ID not found.');
    }
  }

  onChangePasswordClick() {
    const oldPassword = prompt('Enter your old password:');
    const newPassword = prompt('Enter your new password:');

    if (oldPassword && newPassword) {
      this.userService.changePassword(oldPassword, newPassword).subscribe(
        (response) => {
          console.log('Password changed successfully:', response);
          // Handle success (e.g., display a success message)
        },
        (error) => {
          console.error('Error changing password:', error);
          // Handle error (e.g., display an error message)
        }
      );
    }
  }
}
