import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-brand-payment',
  templateUrl: './brand-payment.component.html',
  styleUrls: ['./brand-payment.component.css']
})
export class BrandPaymentComponent implements OnInit {
  paymentDetails: any;

  constructor(private route: ActivatedRoute, private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const postId = +params['id']; // '+' is used to convert the parameter to a number
      this.fetchPaymentDetails(postId);
    });
  }

  fetchPaymentDetails(postId: number): void {
    this.userService.getPaymentDetails(postId).subscribe(
      (paymentDetails) => {
        this.paymentDetails = paymentDetails;
      },
      (error) => {
        console.error('Error fetching payment details:', error);
      }
    );
  }




  onSubmitPayment(postId: number): void {
    const paymentData = {
      postId: postId, // Pass the postId to the server
      amount: this.paymentDetails.amount,
      paymentMethod: 'Credit Card', // Example, update based on your form
      // Add other fields as needed
    };
  
    this.userService.submitPayment(paymentData).subscribe(
      (response) => {
        console.log('Payment submitted successfully:', response);
        // Implement logic for handling successful payment, e.g., navigate to a success page
        this.router.navigate(['/payment-success']);
      },
      (error) => {
        console.error('Error submitting payment:', error);
        const errorMessage = error.message || 'An error occurred while submitting payment.';
        // Implement logic for handling payment error, e.g., display an alert
        alert(errorMessage);
      }
    );
  }
  
}