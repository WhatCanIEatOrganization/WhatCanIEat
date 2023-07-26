import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-images-slide-show',
  templateUrl: './images-slide-show.component.html',
  styleUrls: ['./images-slide-show.component.scss'],
})
export class ImagesSlideShowComponent implements OnInit {
  slides: string[] = [];
  imgSource!: string;

  constructor() { }

  ngOnInit(): void {
    this.slides = this.setSlides();
    this.setTimeoutOnSlideShow();
  }

  setTimeoutOnSlideShow(): void {
    let currSlide = 0;
    this.imgSource = this.slides[currSlide];

    setInterval(() => {
      currSlide === this.slides.length - 1 ? currSlide = 0 : currSlide = currSlide + 1;
      this.imgSource = this.slides[currSlide];
    }, 6000);
  }

  setSlides(): string[] {
    return [
      `/assets/home-page-slides/slide1.jpg`,
      `/assets/home-page-slides/slide2.jpg`,
    ]
  }
}
