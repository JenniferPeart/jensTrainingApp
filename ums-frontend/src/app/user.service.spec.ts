import { TestBed } from '@angular/core/testing';

import { UserService } from './user.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { USERS } from './mock-users';
import { User } from './user';

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
      // Assert - that when the observable resolves, the result matches test data
      expect(result).toEqual(mockUsers);
    });

    // assert that a request is made to the our URl
    let req = httpMock.expectOne('http://localhost:8080/api/v1/getUsers');

    // asset that the request is called using the GET HTTP method
    expect(req.request.method).toBe("GET");

    // respond with mock data, causing Observable to resolve
    // subscribe callback asserts that correct data ws returned
    req.flush(mockUsers);

  });

  it('should call http POST and add a user', () => {
    
    // Arrange
    mockUser = {id: 5, fullName: "Test Name", email: "test@gmail.com"};

    //Act
    service.addUser(mockUser).subscribe( user => {

      //Assert
      expect(user.fullName).toEqual(mockUser.fullName);
    });

    //Assert
    let req = httpMock.expectOne('http://localhost:8080/api/v1/addUser');
    expect(req.request.method).toBe("POST");
    req.flush(mockUser);

  });
  
});
