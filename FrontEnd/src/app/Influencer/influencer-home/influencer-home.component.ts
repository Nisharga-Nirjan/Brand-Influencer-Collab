import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-influencer-home',
  templateUrl: './influencer-home.component.html',
  styleUrls: ['./influencer-home.component.css']
})
export class InfluencerHomeComponent implements OnInit {
  userId: number;
  profiles: any[] = [];
  currentIndex = 0;
  profilesInView = 4;
  postContent: string = '';
  brandPosts: any[] = [];

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.loadAllUserProfiles();
    this.loadBrandPosts();
  }

  loadBrandPosts() {
    this.userService.getInfluencerSeePosts().subscribe(
      (brandPosts) => {
        this.brandPosts = brandPosts;
      },
      (error) => {
        console.error('Error fetching brand posts:', error);
      }
    );
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

  onProfileCardClick(userId: number): void {
    this.router.navigate(['/profile-view', userId]);
  }


  onHireClick(postId: number): void {
    if (postId != null) {
      this.userService.updateUserHireInfo(postId).subscribe(
        (response) => {
          console.log('Hire info stored successfully:', response);
          this.handleSuccessResponse(response);
          // Optionally, you can update the UI or perform other actions after successful storage
        },
        (error) => {
          console.error('Error storing hire info:', error);
          const errorMessage = error.message || 'An error occurred.';
          this.displayAlert(errorMessage);
        }
      );
    } else {
      console.error('Post ID is null or undefined.');
      this.displayAlert('Post ID is null or undefined');
    }
}

handleSuccessResponse(response: any): void {
    if (response && response.message === 'Request has been submitted') {
        this.displayAlert('Collab Request has been submitted.');
    } else if (response && response.message === 'You already requested for this collab') {
        this.displayAlert('You have already requested for this Collab.');
    } else {
        this.displayAlert('Hire info stored successfully.');
    }
}

displayAlert(message: string): void {
    alert(message);
}


navigateToInfluencerOffer(): void {
  if (this.userId) {
    this.router.navigate(['/influencer-offer', this.userId]);
  } else {
    console.error('User ID is null or undefined.');
    this.displayAlert('User ID is null or undefined');
  }
}

handleButtonClick(userId: number): void {
  this.router.navigate(['/profile-view', userId]);
}




}