import { TestBed } from '@angular/core/testing';

import { RecipeGatewayService } from './recipe-gateway.service';

describe('RecipeGatewayService', () => {
  let service: RecipeGatewayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecipeGatewayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
