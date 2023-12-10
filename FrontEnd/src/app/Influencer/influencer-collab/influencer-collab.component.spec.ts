import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfluencerCollabComponent } from './influencer-collab.component';

describe('InfluencerCollabComponent', () => {
  let component: InfluencerCollabComponent;
  let fixture: ComponentFixture<InfluencerCollabComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfluencerCollabComponent]
    });
    fixture = TestBed.createComponent(InfluencerCollabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
