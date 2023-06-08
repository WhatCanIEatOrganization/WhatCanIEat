import { TestBed } from '@angular/core/testing';

import { IngredientListViewService } from './ingredient-list-view.service';

describe('IngredientListViewService', () => {
  let service: IngredientListViewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IngredientListViewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
