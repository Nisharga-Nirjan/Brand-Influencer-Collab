// brand-profile-edit.component.ts
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service'; // Update the path
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-brand-profile-edit',
  templateUrl: './brand-profile-edit.component.html',
  styleUrls: ['./brand-profile-edit.component.css'],
})
export class BrandProfileEditComponent implements OnInit {
  user: any = {}; // Adjust the type based on your user details

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    // Fetch user profile data when the component is initialized
    this.userService.getBrandProfile().subscribe(
      (data) => {
        this.user = data;
      },
      (error) => {
        console.error('Error fetching user profile:', error);
      }
    );
  }

  onSubmit(): void {
    // Submit the updated profile data to the backend
    this.userService.editBrandProfile(this.user).subscribe(
      (response) => {
        console.log('Profile updated successfully:', response);
        
        // Navigate to the brand-profile page
        this.router.navigate(['/brand-profile']); // Adjust the route based on your configuration
      },
      (error) => {
        console.error('Error updating profile:', error);
        // Handle the error, show an error message, etc.
      }
      );
    }
  }