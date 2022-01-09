import { Injectable } from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{

  constructor(private router: Router, private jwtHelperService: JwtHelperService) { }

  canActivate(): boolean {
    const isJwtAuthenticated = this.isJwtAuthenticated();
    if (isJwtAuthenticated) {
      return true;
    }

    this.router.navigate(['sign-in']);
    return false;
  }

  private isJwtAuthenticated(): boolean{
    const token = localStorage.getItem('access_token');

    // there is no token.
    if (!token) {
      return false;
    }

    // check whether the token is expired and return
    // true or false
    const expired = this.jwtHelperService.isTokenExpired(token);
    return !expired;
  }
}
