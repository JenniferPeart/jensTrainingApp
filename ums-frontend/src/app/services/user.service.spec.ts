import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { UserService } from './user.service';
import { USERS } from '../users/mock-users';
import { User } from '../interfaces/user';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  let mockUsers: User[];
  let mockUser: User;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });

    httpMock = TestBed.inject(HttpTestingController);
    service = TestBed.inject(UserService);
    mockUsers = USERS;
  });

  afterEach(() => {
    // once all requests have been provided a response using flush, we can
    // check that all requests were full-filled and there are no outstanding requests
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call http GET and get a list of users', () => {

    // Act
    service.getUsers().subscribe( result => {
      // Assert that when the observable resolves, the result matches test data
      expect(result).toEqual(mockUsers);
    });

    // Assert that a request is made to the our URl
    const req = httpMock.expectOne('http://localhost:8080/api/v1/getUsers');

    // Assert that the request is called using the GET HTTP method
    expect(req.request.method).toBe('GET');

    // respond with mock data, causing Observable to resolve
    // subscribe callback asserts that correct data ws returned
    req.flush(mockUsers);
  });

  it('should call http POST and add a user', () => {

    // Arrange
    mockUser = {id: 5, fullName: 'Test Name', email: 'test@gmail.com'};

    // Act
    service.addUser(mockUser).subscribe( user => {

      // Assert
      expect(user.fullName).toEqual(mockUser.fullName);
    });

    // Assert
    const req = httpMock.expectOne('http://localhost:8080/api/v1/addUser');
    expect(req.request.method).toBe('POST');
    req.flush(mockUser);
  });

  it('should call http PUT and update a user', () => {
    mockUser = {id: 5, fullName: 'Test Name', email: 'test@gmail.com'};
    service.editUser(mockUser).subscribe( user => {
      expect(user.fullName).toEqual(mockUser.fullName);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/v1/editUser');
    expect(req.request.method).toBe('PUT');
    req.flush(mockUser);
  });

  it('should call http DELETE and delete a user', () => {
    mockUser = {id: 5, fullName: 'Den', email: 'den@gamil.com'};
    service.deleteUser(mockUser).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/v1/deleteUser/' + mockUser.id);
    expect(req.request.method).toBe('DELETE');
    req.flush(mockUser);
  });

});
