import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfluencerPaymentComponent } from './influencer-payment.component';

describe('InfluencerPaymentComponent', () => {
  let component: InfluencerPaymentComponent;
  let fixture: ComponentFixture<InfluencerPaymentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfluencerPaymentComponent]
    });
    fixture = TestBed.createComponent(InfluencerPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
