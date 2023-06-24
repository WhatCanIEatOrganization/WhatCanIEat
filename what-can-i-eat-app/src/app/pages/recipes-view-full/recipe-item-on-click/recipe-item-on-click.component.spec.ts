import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeItemOnClickComponent } from './recipe-item-on-click.component';

describe('RecipeItemOnClickComponent', () => {
  let component: RecipeItemOnClickComponent;
  let fixture: ComponentFixture<RecipeItemOnClickComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeItemOnClickComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipeItemOnClickComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
