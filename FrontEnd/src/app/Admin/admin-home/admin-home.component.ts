import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {
  profiles: any[] = [];

  constructor(private userService: UserService) { }

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
}
