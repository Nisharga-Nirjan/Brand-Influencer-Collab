import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-app-registration',
  templateUrl: './app-registration.component.html',
  styleUrls: ['./app-registration.component.css']
})
export class AppRegistrationComponent {

  email: string = '';
  brandName: string = '';
  password: string = '';
  tinNumber: string = '';
  originCity: string = '';
  location: string = '';
  registrationMessage: string = '';

  constructor(private http: HttpClient) {}

  submitForm() {
    const userData = {
      email: this.email,
      name: this.brandName,  // Ensure this matches the backend property name
      password: this.password,
      bin_nid_number: this.tinNumber,  // Ensure this matches the backend property name
      origin_city: this.originCity,
      location: this.location,
    };

    console.log('Form data:', userData);

    this.http.post('http://localhost:8081/user/signup', userData)
      .subscribe(
        (response: any) => {
          console.log('Signup success:', response);
          this.registrationMessage = 'Successfully Registered.';
        },
        (error: any) => {
          console.error('Signup error:', error);
          
          // Check if the error object and message are available
          if (error.error && error.error.message) {
            this.registrationMessage = error.error.message;
          } else {
            // Set a generic error message if the structure is unexpected
            this.registrationMessage = 'Registration failed. Please try again.';
          }
        }
      );
  }
}
