import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/common/auth/auth.service';
import { UserCredentials } from 'src/app/common/auth/models/userCredentials';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  loginCredentialsForm = new FormGroup({
    usernameInput: new FormControl('', [Validators.required]),
    emailInput: new FormControl('', [Validators.required, Validators.email]),
    passwordInput: new FormControl('', Validators.required)
  })

  constructor(
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
  }

  sendLoginRequest(): void {
    let userCreds: UserCredentials = {
      username: this.loginCredentialsForm.value.usernameInput!,
      email: this.loginCredentialsForm.value.emailInput!,
      password: this.loginCredentialsForm.value.passwordInput!
    }

    this.authService.loginUser(userCreds);
  }

}
