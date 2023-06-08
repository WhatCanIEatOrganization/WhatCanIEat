import { TestBed } from '@angular/core/testing';

import { IngredientGatewayService } from './ingredient-gateway.service';

describe('IngredientGatewayService', () => {
  let service: IngredientGatewayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IngredientGatewayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
