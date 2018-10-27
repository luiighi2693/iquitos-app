import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserLogin } from 'app/shared/model/user-login.model';

@Component({
    selector: 'jhi-user-login-detail',
    templateUrl: './user-login-detail.component.html'
})
export class UserLoginDetailComponent implements OnInit {
    userLogin: IUserLogin;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userLogin }) => {
            this.userLogin = userLogin;
        });
    }

    previousState() {
        window.history.back();
    }
}
