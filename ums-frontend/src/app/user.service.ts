import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { User } from './user';

// a service is written for any cross-cutting concerns and may be used by more than one components

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private getUsersURL = 'http://localhost:8080/api/v1/getUsers';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.getUsersURL);
  }

}

// The Observable is just a function, with minimal distinctive characteristics. 
// Observables take in an “observer” (an object with “next”, “error” and “complete” methods on it), 
// and return cancellation logic.