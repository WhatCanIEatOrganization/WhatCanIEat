import { trigger, transition, style, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-advert-banner',
  templateUrl: './advert-banner.component.html',
  styleUrls: ['./advert-banner.component.scss'],
  animations: [
    trigger('carouselAnimation', [
      transition('* => *', [
        style({ opacity: 0.6 }),
        animate('1300ms', style({ opacity: 1 }))
      ]),
      transition('* => *', [
        animate('1300ms', style({ opacity: 0.6 }))
      ])
    ])
  ]
})
export class AdvertBannerComponent implements OnInit {
  slides: string[] = [];
  imgSource!: string;
  animate = true;

  constructor() { }

  ngOnInit(): void {
    this.slides = this.setSlides();
    this.setTimeoutOnSlideShow();
  }

  toggleAnimate(): void {
    this.animate ? this.animate = false : this.animate = true;
  }

  setTimeoutOnSlideShow(): void {
    let currSlide = 0;
    this.imgSource = this.slides[currSlide];

    setInterval(() => {
      currSlide === this.slides.length - 1 ? currSlide = 0 : currSlide = currSlide + 1;
      this.imgSource = this.slides[currSlide];
      this.toggleAnimate();
    }, 15000);
  }

  setSlides(): string[] {
    return [
      `/assets/home-page-slides/slide1.jpg`,
      `/assets/home-page-slides/slide2.jpg`,
    ]
  }
}
