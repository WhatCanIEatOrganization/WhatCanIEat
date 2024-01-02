import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FeedPageService {
  contentLoadingObservable = new BehaviorSubject(false);

  constructor() { }

}
