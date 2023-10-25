import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipesSearchDialogComponent } from './recipes-search-dialog.component';

describe('RecipesSearchDialogComponent', () => {
  let component: RecipesSearchDialogComponent;
  let fixture: ComponentFixture<RecipesSearchDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipesSearchDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipesSearchDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
