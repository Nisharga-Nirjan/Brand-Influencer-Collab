import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfluencerOfferComponent } from './influencer-offer.component';

describe('InfluencerOfferComponent', () => {
  let component: InfluencerOfferComponent;
  let fixture: ComponentFixture<InfluencerOfferComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfluencerOfferComponent]
    });
    fixture = TestBed.createComponent(InfluencerOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
