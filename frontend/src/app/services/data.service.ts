import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  // sharing params from login or forgot password to auth
  messageSourceForgotPassword = new BehaviorSubject(false);
  currentMessageForgotPassword = this.messageSourceForgotPassword.asObservable();

  // sharing params from login or updateProfile to auth
  messageSourceUpdateProfile = new BehaviorSubject(false);
  currentMessageUpdateProfile = this.messageSourceUpdateProfile.asObservable();

  constructor() {}

  setPopupForgotPassword(flag: boolean) {
    this.messageSourceForgotPassword.next(flag);
  }

  setPopupUpdateProfile(flag: boolean) {
    this.messageSourceUpdateProfile.next(flag);
  }
}
