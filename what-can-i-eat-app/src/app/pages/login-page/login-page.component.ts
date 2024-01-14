import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  loginCredentialsForm = new FormGroup({
    emailInput: new FormControl('', [Validators.required, Validators.email]),
    passwordInput: new FormControl('', Validators.required)
  })

  constructor() { }

  ngOnInit(): void {
  }

  sendLoginRequest(): void {
    let userCreds = {
      email: this.loginCredentialsForm.value.emailInput,
      passwordInput: this.loginCredentialsForm.value.passwordInput
    }
  }

}
