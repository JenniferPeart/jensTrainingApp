import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  public userId!: number;

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    const str = this.route.snapshot.paramMap.get('id');
    if (str != null) {
      const id: number = parseInt(str, 10);
      this.userId = id;
    }
  }

  editUser(id: number, fullName: string, email: string): void {
    fullName = fullName.trim();
    email = email.trim();

    if (!fullName) { return; }
    if (!email) { return; }

    this.userService.editUser({id, fullName, email } as User)
    .subscribe( user => {
      this.router.navigate(['/users']);
    });
  }

}
