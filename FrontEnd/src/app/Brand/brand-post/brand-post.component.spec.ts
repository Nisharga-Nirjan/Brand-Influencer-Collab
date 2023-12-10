import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrandPostComponent } from './brand-post.component';

describe('BrandPostComponent', () => {
  let component: BrandPostComponent;
  let fixture: ComponentFixture<BrandPostComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BrandPostComponent]
    });
    fixture = TestBed.createComponent(BrandPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
