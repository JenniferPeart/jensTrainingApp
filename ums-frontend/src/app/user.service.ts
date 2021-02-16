import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

import { User } from './user';

// a service is written for any cross-cutting concerns and may be used by more than one components

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private BASE_URL = 'http://localhost:8080/api/v1/';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {}

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // send the error to remote logging infrastructure
      console.error(error);
      {alert("The system was unable to complete your request to "+ operation)};
      // let the app keep running by returning an empty result
      return of(result as T);
    };
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.BASE_URL}getUsers`).pipe(
      // pipe the observable result from http.get() through an RxJS catchError() operator
      // the catchError() operator intercepts an observable that failed
      // then passes the error to the error handling function
      // the handleError() method reports the error and then returns and innocuous result
      // so that the app keeps working
      catchError(this.handleError<User[]>('get users.', []))
    );
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.BASE_URL}addUser`, user, this.httpOptions).pipe(
      catchError(this.handleError<User>('add a user.'))
    );
  }

  editUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.BASE_URL}editUser`, user, this.httpOptions).pipe(
      catchError(this.handleError<User>('edit a user.'))
    );
  }

  deleteUser(user: User): Observable<User> {
    return this.http.delete<User>(`${this.BASE_URL}deleteUser/${user.id}`, this.httpOptions).pipe(
      catchError(this.handleError<User>('delete a user.'))
    );
  }

}

// The Observable is just a function, with minimal distinctive characteristics. 
// Observables take in an “observer” (an object with “next”, “error” and “complete” methods on it), 
// and return cancellation logic.