import { TestBed } from '@angular/core/testing';

import { UserService } from './user.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { USERS } from './mock-users';
import { User } from './user';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  let mockUsers: User[];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });

    httpMock = TestBed.inject(HttpTestingController);
    service = TestBed.inject(UserService);

  });

  afterEach(() => {
    httpMock.verify(); // make sure there are no outstanding requests
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('should get users from http', () => {

    mockUsers = USERS;

    service.getUsers().subscribe( result => {
      expect(result).toEqual(mockUsers);
    });

    let req = httpMock.expectOne('http://localhost:8080/api/v1/getUsers');
    expect(req.request.method).toBe("GET");
    req.flush(mockUsers);

  });
  
});
