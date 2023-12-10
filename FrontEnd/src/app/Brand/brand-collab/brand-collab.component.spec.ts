import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrandCollabComponent } from './brand-collab.component';

describe('BrandCollabComponent', () => {
  let component: BrandCollabComponent;
  let fixture: ComponentFixture<BrandCollabComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BrandCollabComponent]
    });
    fixture = TestBed.createComponent(BrandCollabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
