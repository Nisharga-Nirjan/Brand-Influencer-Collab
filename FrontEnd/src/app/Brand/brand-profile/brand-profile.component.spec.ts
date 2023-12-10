import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrandProfileComponent } from './brand-profile.component';

describe('BrandProfileComponent', () => {
  let component: BrandProfileComponent;
  let fixture: ComponentFixture<BrandProfileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BrandProfileComponent]
    });
    fixture = TestBed.createComponent(BrandProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
