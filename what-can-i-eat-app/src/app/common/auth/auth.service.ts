import { Injectable } from '@angular/core';
import { AuthGatewayService } from './auth-gateway.service';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { RegisterUserCredentials, UserCredentials } from './models/userCredentials';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private authGatewayService: AuthGatewayService,
  ) { }

  registerUser(userCredentials: RegisterUserCredentials) {
    this.authGatewayService.register(userCredentials).subscribe((val) => {
      console.log(val);
    });
  }

  loginUser(userCredentials: UserCredentials) {
    this.authGatewayService.login(userCredentials).subscribe((val) => {
      console.log(val);
    });
  }
}
