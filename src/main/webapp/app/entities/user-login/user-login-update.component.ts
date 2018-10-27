import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUserLogin } from 'app/shared/model/user-login.model';
import { UserLoginService } from './user-login.service';

@Component({
    selector: 'jhi-user-login-update',
    templateUrl: './user-login-update.component.html'
})
export class UserLoginUpdateComponent implements OnInit {
    userLogin: IUserLogin;
    isSaving: boolean;

    constructor(private userLoginService: UserLoginService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userLogin }) => {
            this.userLogin = userLogin;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userLogin.id !== undefined) {
            this.subscribeToSaveResponse(this.userLoginService.update(this.userLogin));
        } else {
            this.subscribeToSaveResponse(this.userLoginService.create(this.userLogin));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserLogin>>) {
        result.subscribe((res: HttpResponse<IUserLogin>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
