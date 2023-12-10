// influencer-profile-edit.component.ts
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service'; // Update the path
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-influencer-profile-edit',
  templateUrl: './influencer-profile-edit.component.html',
  styleUrls: ['./influencer-profile-edit.component.css'],
})
export class InfluencerProfileEditComponent implements OnInit {
  user: any = {}; // Adjust the type based on your user details

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    // Fetch user profile data when the component is initialized
    this.userService.getInfluencerProfile().subscribe(
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
    this.userService.editInfluencerProfile(this.user).subscribe(
      (response) => {
        console.log('Profile updated successfully:', response);
        
        // Navigate to the influencer-profile page
        this.router.navigate(['/influencer-profile']); // Adjust the route based on your configuration
      },
      (error) => {
        console.error('Error updating profile:', error);
        // Handle the error, show an error message, etc.
      }
      );
    }
  }