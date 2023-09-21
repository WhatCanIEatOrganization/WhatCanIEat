import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipesSearchBoxByIngredientsComponent } from './recipes-search-box-by-ingredients.component';

describe('RecipesSearchBoxByIngredientsComponent', () => {
  let component: RecipesSearchBoxByIngredientsComponent;
  let fixture: ComponentFixture<RecipesSearchBoxByIngredientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipesSearchBoxByIngredientsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipesSearchBoxByIngredientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
