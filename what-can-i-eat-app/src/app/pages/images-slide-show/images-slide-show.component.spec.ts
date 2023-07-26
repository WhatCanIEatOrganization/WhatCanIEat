import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImagesSlideShowComponent } from './images-slide-show.component';

describe('ImagesSlideShowComponent', () => {
  let component: ImagesSlideShowComponent;
  let fixture: ComponentFixture<ImagesSlideShowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImagesSlideShowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImagesSlideShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
