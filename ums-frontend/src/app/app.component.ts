import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { UserService } from './services/user.service';
import { SecurityService } from './services/security.service';
import { Credentials } from './interfaces/credentials';
import { User } from './interfaces/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'User Management System';

  credentials: Credentials = {
    username: 'jen',
    password: 'password',
    isAuthenticated: false,
    isAdmin: true
  };

  constructor(
    private userService: UserService, private securityService: SecurityService, private router: Router, private http: HttpClient) {}

}
