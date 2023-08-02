import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeFavoriteItemComponent } from './recipe-favorite-item.component';

describe('RecipeFavoriteItemComponent', () => {
  let component: RecipeFavoriteItemComponent;
  let fixture: ComponentFixture<RecipeFavoriteItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeFavoriteItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipeFavoriteItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
