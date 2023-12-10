import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-profile-view',
  templateUrl: './profile-view.component.html',
  styleUrls: ['./profile-view.component.css'],
})
export class ProfileViewComponent implements OnInit {
  user: any;

  constructor(private userService: UserService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const userId = this.route.snapshot.params['userId'];

    this.userService.getUserProfileById(userId).subscribe(
      (profile) => {
        console.log('Fetched user profile:', profile);
        // Assign the profile data to component properties for display
        this.user = profile;
      },
      (error) => {
        console.error('Error fetching user profile:', error);
      }
    );
  }
}
