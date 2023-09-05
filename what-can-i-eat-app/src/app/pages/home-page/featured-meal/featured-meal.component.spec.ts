import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeaturedMealComponent } from './featured-meal.component';

describe('FeaturedMealComponent', () => {
  let component: FeaturedMealComponent;
  let fixture: ComponentFixture<FeaturedMealComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FeaturedMealComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FeaturedMealComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
