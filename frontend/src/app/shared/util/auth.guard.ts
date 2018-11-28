import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { Router, ActivatedRoute, Params } from '@angular/router';
import {AuthenticationService} from './authentication.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(
    private _route: ActivatedRoute,
    private _router: Router,
    private authenticationService: AuthenticationService) {}

  canActivate() {

    if (this.authenticationService.isAuthenticated()) {
      return true;
    }

    this._router.navigate(['/authentication/login']);
    return false;
  }


}
