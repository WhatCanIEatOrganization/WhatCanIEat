import { TestBed } from '@angular/core/testing';

import { RecipesViewFullService } from './recipes-view-full.service';

describe('RecipesViewFullService', () => {
  let service: RecipesViewFullService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecipesViewFullService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
