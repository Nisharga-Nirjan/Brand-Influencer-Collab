import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {
  profiles: any[] = [];

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    const decodedToken = this.userService.getDecodedToken();

    if (decodedToken && decodedToken.id) {
      const userId = decodedToken.id;

      this.userService.adminAllProfiles(userId).subscribe(
        (data) => {
          this.profiles = data;
        },
        (error) => {
          console.error('Error fetching profiles:', error);
        }
      );
    } else {
      console.error('Decoded token or user ID not found.');
    }
  }

  onCheckboxChange(profile: any) {
    console.log(`Checkbox changed for profile ${profile.id}. New status: ${profile.status}`);

    // Update the status on the backend
    this.userService.toggleUserStatus(profile.id).subscribe(
      () => {
        console.log(`Backend status updated for profile ${profile.id}.`);
        // No need to reload the page if you want to keep the state in the UI
      },
      (error) => {
        console.error(`Error updating status for profile ${profile.id}:`, error);
      }
    );
  }


  handleButtonClick(userId: number): void {
    this.router.navigate(['/profile-view', userId]);
  }

  onDeleteUserClick(userId: number) {
    const confirmDelete = confirm('Are you sure you want to delete this user?');

    if (confirmDelete) {
      this.userService.deleteUser(userId).subscribe(
        (response) => {
          console.log('User deleted successfully:', response);
          // Optionally, you can update the UI to remove the deleted user from the list
          this.profiles = this.profiles.filter((profile) => profile.id !== userId);
        },
        (error) => {
          console.error('Error deleting user:', error);
          // Handle error (e.g., display an error message)
        }
      );
    }
  }


}
