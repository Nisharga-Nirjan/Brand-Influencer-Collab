// brand-home.component.ts

import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-brand-home',
  templateUrl: './brand-home.component.html',
  styleUrls: ['./brand-home.component.css'],
})
export class BrandHomeComponent implements OnInit {
  profiles: any[] = [];
  
  postContent: string = '';

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.loadAllUserProfiles();
  }

  loadAllUserProfiles() {
    this.userService.getAllUserProfiles().subscribe(
      (profiles) => {
        console.log('Fetched profiles:', profiles);
        // Limit the number of profiles to display
        this.profiles = profiles.slice(0, 10);
      },
      (error) => {
        console.error('Error fetching user profiles:', error);
      }
    );
  }

  postJob() {
    if (this.postContent.trim() !== '') {
      const postData = { post: this.postContent };
      this.userService.jobPost(postData).subscribe(
        (response: any) => {
          console.log('Post success:', response);
          this.showSuccessAlert();
          this.postContent = '';
        },
        (error: any) => {
          console.error('Post error:', error);
        }
      );
    } else {
      console.error('Post content is empty.');
      this.showFailAlert();
    }
  }
  showSuccessAlert() {
    window.alert('Successfully Posted!');
  }
  showFailAlert() {
    window.alert('Post Box Cannot Be Empty');
  }
  onProfileCardClick(userId: number): void {
    this.router.navigate(['/profile-view', userId]);
  }
  

  handleButtonClick(userId: number): void {
    this.router.navigate(['/profile-view', userId]);
  }





}
