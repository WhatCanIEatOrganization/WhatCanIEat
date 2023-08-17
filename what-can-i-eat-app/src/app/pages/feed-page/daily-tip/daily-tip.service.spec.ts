import { TestBed } from '@angular/core/testing';

import { DailyTipService } from './daily-tip.service';

describe('DailyTipService', () => {
  let service: DailyTipService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DailyTipService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
