import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserCredentials } from './models/userCredentials';

@Injectable({
  providedIn: 'root'
})
export class AuthGatewayService {
  apiURL = environment.apiURL;

  constructor(
    private http: HttpClient,
  ) { }

  register(): Observable<UserCredentials> {
    let userCredentials: UserCredentials = {
      id: 99,
      name: "janek",
      email: "janek99@wp.pl",
      password: "haslojanka103"
    }

    console.log(userCredentials);
    return this.http.post<UserCredentials>(`${this.apiURL}/v3/auth/validate`, userCredentials);
  }
}
