import { Component, OnInit } from '@angular/core';
import { DataService } from '@app/services/data.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {
  openPopupForgotPasswordFlag: boolean;
  openPopupForgotPasswordSmsEmailFlag: boolean;
  openPopupForgotPasswordQuestionsFlag: boolean;
  forgotPasswordSendOptionSelected = 'sms/email';
  checkSmsNotificationFlag: boolean;

  checkEmailNotificationFlag: boolean;
  openPopupNotificationInputFlag: boolean;

  openPopupUpdateProfileFlag: boolean;

  constructor(private data: DataService) {}

  ngOnInit() {
    this.openPopupForgotPasswordSmsEmailFlag = false;
    this.openPopupForgotPasswordQuestionsFlag = false;

    this.checkSmsNotificationFlag = true;
    this.checkEmailNotificationFlag = false;
    this.openPopupNotificationInputFlag = false;

    this.data.currentMessageForgotPassword.subscribe(message => (this.openPopupForgotPasswordFlag = message));
    this.data.currentMessageUpdateProfile.subscribe(message => (this.openPopupUpdateProfileFlag = message));
  }

  setDefaultValues() {
    this.openPopupForgotPasswordFlag = false;
    this.openPopupForgotPasswordSmsEmailFlag = false;
    this.openPopupForgotPasswordQuestionsFlag = false;

    this.checkSmsNotificationFlag = true;
    this.checkEmailNotificationFlag = false;

    this.openPopupNotificationInputFlag = false;
  }

  closeForgotPassPopup() {
    this.setDefaultValues();
    this.forgotPasswordSendOptionSelected = 'sms/email';
  }

  showForgotPasswordSte1Popup() {
    this.setDefaultValues();

    this.openPopupForgotPasswordSmsEmailFlag = this.forgotPasswordSendOptionSelected === 'sms/email';
    this.openPopupForgotPasswordQuestionsFlag = this.forgotPasswordSendOptionSelected === 'questions';
  }

  showNotificationInputPopup() {
    this.setDefaultValues();

    this.openPopupNotificationInputFlag = !this.openPopupNotificationInputFlag;
  }

  changeValueSelectedNotificationOption(value: string) {
    if (value === 'sms') {
      this.checkSmsNotificationFlag = !this.checkSmsNotificationFlag;
    }

    if (value === 'email') {
      this.checkEmailNotificationFlag = !this.checkEmailNotificationFlag;
    }
  }
}
