// influencer-profile-edit.component.ts
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service'; // Update the path
import { Router } from '@angular/router';
import { JSEncrypt } from 'jsencrypt';

@Component({
  selector: 'app-influencer-profile-edit',
  templateUrl: './influencer-profile-edit.component.html',
  styleUrls: ['./influencer-profile-edit.component.css'],
})
export class InfluencerProfileEditComponent implements OnInit {
  user: any = {};
  publicKey: string | null = null;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getInfluencerProfile().subscribe(
      (data) => {
        this.user = data;
        this.decryptData();
        this.fetchPublicKey();
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

  fetchPublicKey() {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('JWT token not found in local storage.');
      return;
    }
    const decodedToken = this.parseJwt(token);
    if (!decodedToken || !decodedToken.publicKey) {
      console.error('Public key not found in JWT token.');
      return;
    }
    this.publicKey = decodedToken.publicKey;
  }

  parseJwt(token: string) {
    try {
      return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
      return null;
    }
  }

  onSubmit(): void {
    // Encryption of updated data
    const encryptor = new JSEncrypt();
    if (!this.publicKey) {
      console.error('Public key not found.');
      return;
    }
    encryptor.setPublicKey(this.publicKey);

    this.user.location = encryptor.encrypt(this.user.location);
    this.user.origin_city = encryptor.encrypt(this.user.origin_city);
    this.user.phone = encryptor.encrypt(this.user.phone);
    this.user.established_year = encryptor.encrypt(this.user.established_year);
    this.user.facebook = encryptor.encrypt(this.user.facebook);
    this.user.instagram = encryptor.encrypt(this.user.instagram);
    this.user.twitter = encryptor.encrypt(this.user.twitter);
    this.user.youtube = encryptor.encrypt(this.user.youtube);


    this.userService.editInfluencerProfile(this.user).subscribe(
      (response) => {
        console.log('Profile updated successfully:', response);
        
        this.router.navigate(['/influencer-profile']);
      },
      (error) => {
        console.error('Error updating profile:', error);
        // Handle the error, show an error message, etc.
      }
    );
  }
}