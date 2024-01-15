import { Injectable } from '@angular/core';
import { AuthGatewayService } from './auth-gateway.service';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { UserCredentials } from './models/userCredentials';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private authGatewayService: AuthGatewayService,
  ) { }

  registerUser() {
    this.authGatewayService.register().subscribe((val) => {
      console.log(val);
    });
  }
}
