import { animate, style, transition, trigger } from '@angular/animations';
import { BreakpointObserver, BreakpointState, Breakpoints, MediaMatcher } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, EventEmitter, OnInit, Output, Renderer2 } from '@angular/core';
import { Observable, map } from 'rxjs';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss'],
  animations: [
    trigger(
      'enterAnimation', [
        transition(':enter', [
          style({transform: 'translateX(100%)', opacity: 0}),
          animate('250ms', style({transform: 'translateX(0)', opacity: 1}))
        ]),
        transition(':leave', [
          style({transform: 'translateX(0)', opacity: 1}),
          animate('250ms', style({transform: 'translateX(100%)', opacity: 0}))
        ])
      ]
    )
  ],
})
export class NavBarComponent implements OnInit {
  @Output() showMenuOverlay = new EventEmitter<boolean>();
  mobileQuery!: MediaQueryList;
  showMenuButton = false;
  showMenu = false;

  constructor(
    private renderer: Renderer2,
    private breakpointObserver: BreakpointObserver,
  ) {

    this.breakpointObserver.observe([
      "(min-width: 768px)"
    ]).subscribe((result: BreakpointState) => {
      if (result.matches) {
        this.showMenuButton = false;
      } else {
        this.showMenuButton = true;
      }
    });
   }

  ngOnInit(): void {
    
  }

  toggleMenu(): void {
    if(this.showMenu) {
      this.renderer.removeClass(document.body, 'disable-scroll');
      this.showMenu = false;
      this.showMenuOverlay.emit(false);
    } else {
      this.renderer.addClass(document.body, 'disable-scroll');
      this.showMenu = true;
      this.showMenuOverlay.emit(true);
    }    
  }
}
