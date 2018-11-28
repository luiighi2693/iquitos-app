import { TestBed } from '@angular/core/testing';

import { FullService } from './full.service';

describe('FullService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FullService = TestBed.get(FullService);
    expect(service).toBeTruthy();
  });
});
