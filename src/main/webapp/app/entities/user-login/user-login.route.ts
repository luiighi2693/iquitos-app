import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserLogin } from 'app/shared/model/user-login.model';
import { UserLoginService } from './user-login.service';
import { UserLoginComponent } from './user-login.component';
import { UserLoginDetailComponent } from './user-login-detail.component';
import { UserLoginUpdateComponent } from './user-login-update.component';
import { UserLoginDeletePopupComponent } from './user-login-delete-dialog.component';
import { IUserLogin } from 'app/shared/model/user-login.model';

@Injectable({ providedIn: 'root' })
export class UserLoginResolve implements Resolve<IUserLogin> {
    constructor(private service: UserLoginService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((userLogin: HttpResponse<UserLogin>) => userLogin.body));
        }
        return of(new UserLogin());
    }
}

export const userLoginRoute: Routes = [
    {
        path: 'user-login',
        component: UserLoginComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserLogins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-login/:id/view',
        component: UserLoginDetailComponent,
        resolve: {
            userLogin: UserLoginResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserLogins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-login/new',
        component: UserLoginUpdateComponent,
        resolve: {
            userLogin: UserLoginResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserLogins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-login/:id/edit',
        component: UserLoginUpdateComponent,
        resolve: {
            userLogin: UserLoginResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserLogins'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userLoginPopupRoute: Routes = [
    {
        path: 'user-login/:id/delete',
        component: UserLoginDeletePopupComponent,
        resolve: {
            userLogin: UserLoginResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserLogins'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
