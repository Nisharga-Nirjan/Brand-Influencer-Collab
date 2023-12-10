import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfluencerProfileEditComponent } from './influencer-profile-edit.component';

describe('InfluencerProfileEditComponent', () => {
  let component: InfluencerProfileEditComponent;
  let fixture: ComponentFixture<InfluencerProfileEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfluencerProfileEditComponent]
    });
    fixture = TestBed.createComponent(InfluencerProfileEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
