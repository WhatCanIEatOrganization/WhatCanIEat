import { TestBed } from '@angular/core/testing';

import { FeedPageService } from './feed-page.service';

describe('FeedPageService', () => {
  let service: FeedPageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FeedPageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
