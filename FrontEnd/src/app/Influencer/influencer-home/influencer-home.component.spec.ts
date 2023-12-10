import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfluencerHomeComponent } from './influencer-home.component';

describe('InfluencerHomeComponent', () => {
  let component: InfluencerHomeComponent;
  let fixture: ComponentFixture<InfluencerHomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfluencerHomeComponent]
    });
    fixture = TestBed.createComponent(InfluencerHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
