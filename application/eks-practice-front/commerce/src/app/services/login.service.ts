import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, map, Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {User} from "../models/User.model";
import {AuthenticationResponse} from "../models/authentication-response";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private currentMemberSubject: BehaviorSubject<User>;
  private currentMember: Observable<User>;

  private baseUrl = environment.host + environment.authenticationUri;

  constructor(private httpClient: HttpClient) {
    this.currentMemberSubject = new BehaviorSubject<User>(JSON.parse(<string>localStorage.getItem('currentMember')));
    this.currentMember = this.currentMemberSubject.asObservable();
  }

  public get currentMemberValue(): User {
    return this.currentMemberSubject.value;
  }

  login(email: string, password: string): Observable<User> {
    return this.httpClient.post<AuthenticationResponse>(this.baseUrl, { email, password })
      .pipe(map(response => {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        const member = new User(response.id, response.email, response.fullName, response.phone,
          response.lastLoginAt, response.updatedAt, response.createdAt, response.jwt, ['USER']);
        localStorage.setItem('token', response.jwt);
        localStorage.setItem('currentMember', JSON.stringify(member));
        this.currentMemberSubject.next(member);
        return member;
      }));
  }

}
