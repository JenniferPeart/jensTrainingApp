import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { UserService } from '../services/user.service';
import { Credentials } from '../interfaces/credentials';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {

  constructor(private userService: UserService, private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }

}

// RESOURCES FOR LOG IN
// https://spring.io/guides/tutorials/spring-security-and-angular-js/#_the_login_page_angular_js_and_spring_security_part_ii
// https://jasonwatmore.com/post/2020/04/29/angular-9-basic-http-authentication-tutorial-example
