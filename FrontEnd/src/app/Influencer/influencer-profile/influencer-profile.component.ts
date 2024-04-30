import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { JSEncrypt } from 'jsencrypt';

@Component({
  selector: 'app-influencer-profile',
  templateUrl: './influencer-profile.component.html',
  styleUrls: ['./influencer-profile.component.css'],
})
export class InfluencerProfileComponent implements OnInit {
  user: any;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getUserProfile().subscribe(
      (data) => {
        this.user = data;
        
        this.decryptData();
      },
      (error) => {
        console.error('Error fetching user profile:', error);
      }
    );
  }

  decryptData() {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('JWT token not found in local storage.');
      return;
    }
    const decodedToken = this.parseJwt(token);
    if (!decodedToken || !decodedToken.privateKey) {
      console.error('Private key not found in JWT token.');
      return;
    }
    const privateKey = decodedToken.privateKey;
    const decryptor = new JSEncrypt();
    decryptor.setPrivateKey(privateKey);

    // Decryption
    this.user.location = decryptor.decrypt(this.user.location);
    this.user.origin_city = decryptor.decrypt(this.user.origin_city);
    this.user.phone = decryptor.decrypt(this.user.phone);
    this.user.established_year = decryptor.decrypt(this.user.established_year);
    this.user.facebook = decryptor.decrypt(this.user.facebook);
    this.user.instagram = decryptor.decrypt(this.user.instagram);
    this.user.twitter = decryptor.decrypt(this.user.twitter);
    this.user.youtube = decryptor.decrypt(this.user.youtube);
  }

  parseJwt(token: string) {
    try {
      return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
      return null;
    }
  }

  onEditProfileClick() {
    if (this.user && this.user.id) {
      this.router.navigate(['/influencer-profile-edit', this.user.id]);
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