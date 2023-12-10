import { Component } from '@angular/core';

@Component({
  selector: 'app-influencer-payment',
  templateUrl: './influencer-payment.component.html',
  styleUrls: ['./influencer-payment.component.css']
})
export class InfluencerPaymentComponent {

}



//INFLUENCER.HOME.COMPONENT.TS
/*
import { Component, HostListener } from '@angular/core';
import { trigger, style, animate, state, transition } from '@angular/animations';


@Component({
  selector: 'app-influencer-home',
  templateUrl: './influencer-home.component.html',
  styleUrls: ['./influencer-home.component.css'],
  animations: [
    trigger('slideInOut', [
      state('in', style({
        transform: 'translateX(0)',
      })),
      state('out', style({
        transform: 'translateX(-100%)',
      })),
      transition('in => out', animate('300ms ease-in-out')),
      transition('out => in', animate('300ms ease-in-out')),
    ]),
  ],
})
export class InfluencerHomeComponent {
  profiles = Array.from({ length: 10 }).map((_, index) => ({
    name: `Profile ${index + 1}`,
    contactEmail: `contact${index + 1}@example.com`,
    profession: `Profession ${index + 1}`,
    dob: `DOB ${index + 1}`
  }));

  currentIndex = 0;
  profilesInView = 4;

  @HostListener('wheel', ['$event'])
  handleWheel(event: WheelEvent) {
    if (event.deltaY > 0) {
      this.scrollRight();
    } else {
      this.scrollLeft();
    }
  }

  scrollLeft() {
    this.currentIndex = (this.currentIndex - 1 + this.profiles.length) % this.profiles.length;
  }

  scrollRight() {
    this.currentIndex = (this.currentIndex + 1) % this.profiles.length;
  }
}

*/