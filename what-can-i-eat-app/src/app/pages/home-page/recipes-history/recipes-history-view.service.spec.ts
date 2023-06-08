import { TestBed } from '@angular/core/testing';

import { RecipesHistoryViewService } from './recipes-history-view.service';

describe('RecipesHistoryViewService', () => {
  let service: RecipesHistoryViewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecipesHistoryViewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
