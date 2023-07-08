import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipesViewFullComponent } from './recipes-view-full.component';

describe('RecipesViewFullComponent', () => {
  let component: RecipesViewFullComponent;
  let fixture: ComponentFixture<RecipesViewFullComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipesViewFullComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipesViewFullComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
