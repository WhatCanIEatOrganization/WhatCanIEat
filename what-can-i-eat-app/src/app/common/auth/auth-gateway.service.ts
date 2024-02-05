import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RegisterUserCredentials, UserCredentials } from './models/userCredentials';

@Injectable({
  providedIn: 'root'
})
export class AuthGatewayService {
  apiURL = environment.apiURL;

  constructor(
    private http: HttpClient,
  ) { }

  register(userCredentials: RegisterUserCredentials): Observable<RegisterUserCredentials> {
    console.log(userCredentials);
    return this.http.post<RegisterUserCredentials>(`${this.apiURL}/v3/auth/register`, userCredentials);
  }

  login(userCredentials: UserCredentials): Observable<UserCredentials> {
    console.log(userCredentials);
    return this.http.post<UserCredentials>(`${this.apiURL}/v3/auth/login`, userCredentials);
  }
}
