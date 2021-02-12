import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'

import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  title = 'Users'; 
  users!: User[];

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers().subscribe(
      response => {
        this.users = response
      },
      err => {alert("Error occurred while getting the users list")}
      );
  }

  addUser(fullName: string, email: string): void {
    fullName = fullName.trim();
    email = email.trim();

    if (!fullName) { return; }
    if (!email) { return; }

    this.userService.addUser({ fullName, email } as User)
      .subscribe(user => {
        this.users.push(user);
      });
  }

  deleteUser(user: User): void {
    this.users = this.users.filter(u => u !== user);
    this.userService.deleteUser(user).subscribe();
  }

}
