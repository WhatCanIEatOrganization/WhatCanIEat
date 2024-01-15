import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/common/auth/auth.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  constructor(
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
  }

  registerUser(): void {
    this.authService.registerUser();
  }

}
