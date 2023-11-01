import { TestBed } from '@angular/core/testing';

import { RecipeDetailsDialogService } from './recipe-details-dialog.service';

describe('RecipeDetailsDialogService', () => {
  let service: RecipeDetailsDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecipeDetailsDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
