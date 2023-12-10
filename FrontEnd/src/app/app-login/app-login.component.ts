import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-app-login',
  templateUrl: './app-login.component.html',
  styleUrls: ['./app-login.component.css']
})
export class AppLoginComponent {

  email: string = '';
  password: string = '';
  loginMessage: string = '';

  constructor(private http: HttpClient, private router: Router, private userService: UserService) {}

  submitForm(event: Event) {
    event.preventDefault();
    console.log('Submit form function called');
    const userData = {
      email: this.email,
      password: this.password,
    };

    this.http.post('http://localhost:8081/user/login', userData)
      .subscribe(
        (response: any) => {
          console.log('Login success:', response);
          const tokenPayload = JSON.parse(atob(response.token.split('.')[1]));
          const role = tokenPayload.role;
          localStorage.setItem('token', response.token);
          window.location.reload();
          if (role === 'brand') {
            this.router.navigate(['/brand-home']);
          } else if (role === 'influencer') {
            this.router.navigate(['/influencer-home']);
          }
        },
        (error: any) => {
          console.error('Login error:', error);
          if (error.error && error.error.message && error.error.message.toLowerCase() === 'bad credentials.') {
            this.loginMessage = 'Wrong Email ID or Password. Please try again.';
          } else if (error.error && error.error.message) {
            this.loginMessage = error.error.message;
          } else {
            this.loginMessage = 'Login failed. Please try again.';
          }
        }
      );
  }

  forgotPassword() {
    const userEmail = window.prompt('Enter your email:');
    
    if (userEmail) {
      this.userService.forgotPassword(userEmail)
        .subscribe(
          (response: any) => {
            console.log('Forgot password success:', response);
            alert('Check your email for instructions to reset your password.');
          },
          (error: any) => {
            console.error('Forgot password error:', error);
            alert('Failed to initiate password reset. Please try again.');
          }
        );
    }
  }
  goToRegistration() {
    this.router.navigate(['/app-registration']);
  }


}
