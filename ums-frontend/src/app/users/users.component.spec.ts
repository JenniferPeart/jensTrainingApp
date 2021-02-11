import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersComponent } from './users.component';
import { User } from '../user';
import { USERS } from '../mock-users';
import { UserService } from '../user.service';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { routes } from '../app-routing.module';

describe('UsersComponent', () => {
  let component: UsersComponent;
  let fixture: ComponentFixture<UsersComponent>;
  let mockUsers: User[];
  let mockUser: User;
  let mockService: any;

  beforeEach(() => {
    mockUsers = USERS;
    mockUser = {id: 5, fullName: "testName", email: "test@email.com"}

    mockService = {
      getUsers: jasmine.createSpy('getUsers'),
      addUser: jasmine.createSpy('addUser'),
      editUser: jasmine.createSpy('editUser')
    };

    TestBed.configureTestingModule({
      imports: [ RouterTestingModule.withRoutes(routes) ],
      declarations: [ UsersComponent ],
      providers: [
        { provide: UserService, useValue: mockService },
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersComponent); 
    component = fixture.componentInstance; 
    // userService = TestBed.inject(UserService);

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
    
  it('should display the added user in the list of users', () => {
    mockService.getUsers.and.returnValue(of(mockUsers));
    mockService.addUser.and.returnValue(of(mockUser));
    fixture.detectChanges();
    component.addUser("testName", "test@email.com");
    expect(component.users).toContain(mockUser);
  });

  // it('should navigate to /edit-user/id', () => {
  //   component.clickEdit(mockUser);

  // });

});
