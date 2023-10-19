import { Component } from '@angular/core';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  menuOverlay = false;
  title = 'what-can-i-eat-app';
  apiURL = environment.apiURL;

  toggleMenuOverlay(toggle: boolean): void {
    this.menuOverlay = toggle;
  }
}
