import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../services/user.service';
import { JSEncrypt } from 'jsencrypt';

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
        this.decryptData(profile.privateKey);
      },
      (error) => {
        console.error('Error fetching user profile:', error);
      }
    );
  }

  decryptData(privateKey: string | null) {
    if (!privateKey) {
      console.error('Private key not found.');
      return;
    }
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
}
