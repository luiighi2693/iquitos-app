import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProviderAccount } from 'app/shared/model/provider-account.model';
import { ProviderAccountService } from './provider-account.service';
import { ProviderAccountComponent } from './provider-account.component';
import { ProviderAccountDetailComponent } from './provider-account-detail.component';
import { ProviderAccountUpdateComponent } from './provider-account-update.component';
import { ProviderAccountDeletePopupComponent } from './provider-account-delete-dialog.component';
import { IProviderAccount } from 'app/shared/model/provider-account.model';

@Injectable({ providedIn: 'root' })
export class ProviderAccountResolve implements Resolve<IProviderAccount> {
    constructor(private service: ProviderAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((providerAccount: HttpResponse<ProviderAccount>) => providerAccount.body));
        }
        return of(new ProviderAccount());
    }
}

export const providerAccountRoute: Routes = [
    {
        path: 'provider-account',
        component: ProviderAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProviderAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provider-account/:id/view',
        component: ProviderAccountDetailComponent,
        resolve: {
            providerAccount: ProviderAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProviderAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provider-account/new',
        component: ProviderAccountUpdateComponent,
        resolve: {
            providerAccount: ProviderAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProviderAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provider-account/:id/edit',
        component: ProviderAccountUpdateComponent,
        resolve: {
            providerAccount: ProviderAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProviderAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const providerAccountPopupRoute: Routes = [
    {
        path: 'provider-account/:id/delete',
        component: ProviderAccountDeletePopupComponent,
        resolve: {
            providerAccount: ProviderAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProviderAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
