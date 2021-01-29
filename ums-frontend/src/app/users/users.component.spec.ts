import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersComponent } from './users.component';
import { User } from '../user';
import { USERS } from '../mock-users';
import { UserService } from '../user.service';
import { of } from 'rxjs';

describe('UsersComponent', () => {
  let component: UsersComponent;
  let fixture: ComponentFixture<UsersComponent>;
  let mockUsers: User[];

  beforeEach(() => {
    mockUsers = USERS;

    TestBed.configureTestingModule({
      declarations: [ UsersComponent ],
      providers: [
        {provide: UserService, useValue: { getUsers: () => of(mockUsers) } },
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance; 
  });

  it('should create the Users component', () => {
    expect(component).toBeTruthy();
  });

  it('should have a list of users', () => {
    component.ngOnInit();
    expect(component.users).toEqual(mockUsers);
  });

});
