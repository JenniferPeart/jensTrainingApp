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
  let mockUser: User;
  let mockService: any;
  let userService: UserService;

  beforeEach(() => {
    mockUsers = USERS;
    mockUser = {id: 5, fullName: "testName", email: "test@email.com"}

    mockService = {
      getUsers: jasmine.createSpy('getUsers'),
      addUser: jasmine.createSpy('addUser')
    };

    TestBed.configureTestingModule({
      declarations: [ UsersComponent ],
      providers: [
        { provide: UserService, useValue: mockService },
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersComponent); 
    component = fixture.componentInstance; 
    userService = TestBed.inject(UserService);

  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should make a call to get a list of users', () => {
    mockService.getUsers.and.returnValue(of(mockUsers));
    fixture.detectChanges();
    expect(mockService.getUsers).toHaveBeenCalledTimes(1);

  });

  it('should store the list of users', () => {
    mockService.getUsers.and.returnValue(of(mockUsers));
    fixture.detectChanges();
    expect(component.users).toEqual(mockUsers);
  });

  it('should make a call to add a user', () => {
    mockService.getUsers.and.returnValue(of(mockUsers));
    mockService.addUser.and.returnValue(of(mockUser));
    fixture.detectChanges();
    component.addUser("testName", "test@email.com");
    expect(mockService.addUser).toHaveBeenCalledTimes(1);
  });
    
  it('should display the added user is the list of users', () => {
    mockService.getUsers.and.returnValue(of(mockUsers));
    mockService.addUser.and.returnValue(of(mockUser));
    fixture.detectChanges();
    component.addUser("testName", "test@email.com");
    expect(component.users).toContain(mockUser);
  });

});
