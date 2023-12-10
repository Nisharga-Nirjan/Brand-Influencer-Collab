import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router'; // Import the Router

@Component({
  selector: 'app-brand-post',
  templateUrl: './brand-post.component.html',
  styleUrls: ['./brand-post.component.css']
})
export class BrandPostComponent implements OnInit {
  user: any;
  posts: any[] = [];
  influencersVisibilityMap: Map<number, boolean> = new Map();

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getUserProfile().subscribe(
      (data) => {
        this.user = data;
        this.loadBrandPosts();
      },
      (error) => {
        console.error('Error fetching user profile:', error);
      }
    );
  }

  loadBrandPosts() {
    if (this.user && this.user.id) {
      this.userService.getBrandPosts(this.user.id).subscribe(
        (posts) => {
          this.posts = posts;
        },
        (error) => {
          console.error('Error fetching brand posts:', error);
        }
      );
    }
  }

  seeRequestListInfo(postId: number): void {
    if (postId != null) {
      this.influencersVisibilityMap.set(postId, !this.influencersVisibilityMap.get(postId));
      
      if (this.influencersVisibilityMap.get(postId) && !this.posts.find(post => post.post_id === postId).influencers) {
        this.userService.seeRequestListInfo(postId).subscribe(
          (response) => {
            const postIndex = this.posts.findIndex(post => post.post_id === postId);
            if (postIndex !== -1) {
              this.posts[postIndex].influencers = response;
            }
          },
          (error) => {
            console.error('Error getting request list info:', error);
          }
        );
      }
    } else {
      console.error('Post ID is null or undefined.');
    }
  }

  onProfileCardClick(userId: number): void {
    this.router.navigate(['/profile-view', userId]);
  }





  onHireClick(id: number, event: Event): void {
    event.stopPropagation();
    this.userService.updateOfferStatus(id, 'Accepted').subscribe(
        (response) => {
            console.log('Offer status updated successfully:', response);
            this.handleResponseHire(response);
        },
        (error) => {
            console.error('Error updating offer status:', error);
            const errorMessage = error.message || 'An error occurred.';
            this.displayAlert(errorMessage);
        }
    );
}

onRejectClick(id: number, event: Event): void {
    event.stopPropagation();
    this.userService.updateOfferStatus(id, 'Rejected').subscribe(
        (response) => {
            console.log('Offer status updated successfully:', response);
            this.handleResponseReject(response);
        },
        (error) => {
            console.error('Error updating offer status:', error);
            const errorMessage = error.message || 'An error occurred.';
            this.displayAlert(errorMessage);
        }
    );
}

handleResponseHire(response: any): void {
    if (response && response.message === 'Status has already been updated from Pending. You can\'t change now for this post') {
        this.displayAlert('You already Hired or Rejected the Influencer for this Collab!');
    } else {
        this.displayAlert('Influencer Hired!');
    }
}


handleResponseReject(response: any): void {
  if (response && response.message === 'Status has already been updated from Pending. You can\'t change now for this post') {
      this.displayAlert('You already Hired or Rejected the Influencer for this Collab!');
  } else {
      this.displayAlert('Influencer Rejected!');
  }
}





displayAlert(message: string): void {
  alert(message);
}



}