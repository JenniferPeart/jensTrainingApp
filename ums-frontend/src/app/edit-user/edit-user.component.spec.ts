import { ComponentFixture, TestBed } from '@angular/core/testing';
import { User } from '../interfaces/user';
import { USERS } from '../users/mock-users';
import { UserService } from '../services/user.service';
import { of } from 'rxjs';
import { EditUserComponent } from './edit-user.component';
import { RouterTestingModule } from '@angular/router/testing';
import { routes } from '../app-routing.module';

describe('EditUserComponent', () => {
  let component: EditUserComponent;
  let fixture: ComponentFixture<EditUserComponent>;
  let mockUsers: User[];
  let mockUser: User;
  let mockService: any;

  beforeEach(() => {
    mockUsers = USERS;
    mockUser = {id: 5, fullName: 'testName', email: 'test@email.com'};

    mockService = {
      getUsers: jasmine.createSpy('getUsers'),
      addUser: jasmine.createSpy('addUser'),
      editUser: jasmine.createSpy('editUser')
    };

    TestBed.configureTestingModule({
      imports: [ RouterTestingModule.withRoutes(routes) ],
      declarations: [ EditUserComponent ],
      providers: [
        { provide: UserService, useValue: mockService },
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditUserComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should make a call to edit a user', () => {
    mockService.getUsers.and.returnValue(of(mockUsers));
    mockService.editUser.and.returnValue(of(mockUser));
    fixture.detectChanges();
    component.editUser(5, 'testName', 'test@email.com');
    expect(mockService.editUser).toHaveBeenCalledTimes(1);
  });

});
