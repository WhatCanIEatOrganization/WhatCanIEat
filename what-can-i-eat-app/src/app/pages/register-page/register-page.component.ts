import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from 'src/app/common/auth/auth.service';
import { RegisterUserCredentials, UserCredentials } from 'src/app/common/auth/models/userCredentials';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {
  registerNewUserForm = new FormGroup({
    emailInput: new FormControl('', [Validators.required, Validators.email]),
    usernameInput: new FormControl('', [Validators.required]),
    passwordInput: new FormControl('', [Validators.required])
  })

  constructor(
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
  }

  registerUser(): void {
    let userCredentials: RegisterUserCredentials = {
      id: 0,
      name: this.registerNewUserForm.value.usernameInput!,
      email: this.registerNewUserForm.value.emailInput!,
      password: this.registerNewUserForm.value.passwordInput!
    }
    this.authService.registerUser(userCredentials);
  }

}
