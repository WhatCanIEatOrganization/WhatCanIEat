import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipesFavoriteComponent } from './recipes-favorite.component';

describe('RecipesFavoriteComponent', () => {
  let component: RecipesFavoriteComponent;
  let fixture: ComponentFixture<RecipesFavoriteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipesFavoriteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipesFavoriteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
