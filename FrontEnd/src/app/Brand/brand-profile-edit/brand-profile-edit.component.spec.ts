import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrandProfileEditComponent } from './brand-profile-edit.component';

describe('BrandProfileEditComponent', () => {
  let component: BrandProfileEditComponent;
  let fixture: ComponentFixture<BrandProfileEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BrandProfileEditComponent]
    });
    fixture = TestBed.createComponent(BrandProfileEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
