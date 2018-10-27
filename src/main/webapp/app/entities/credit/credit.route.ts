import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Credit } from 'app/shared/model/credit.model';
import { CreditService } from './credit.service';
import { CreditComponent } from './credit.component';
import { CreditDetailComponent } from './credit-detail.component';
import { CreditUpdateComponent } from './credit-update.component';
import { CreditDeletePopupComponent } from './credit-delete-dialog.component';
import { ICredit } from 'app/shared/model/credit.model';

@Injectable({ providedIn: 'root' })
export class CreditResolve implements Resolve<ICredit> {
    constructor(private service: CreditService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((credit: HttpResponse<Credit>) => credit.body));
        }
        return of(new Credit());
    }
}

export const creditRoute: Routes = [
    {
        path: 'credit',
        component: CreditComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Credits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'credit/:id/view',
        component: CreditDetailComponent,
        resolve: {
            credit: CreditResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Credits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'credit/new',
        component: CreditUpdateComponent,
        resolve: {
            credit: CreditResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Credits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'credit/:id/edit',
        component: CreditUpdateComponent,
        resolve: {
            credit: CreditResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Credits'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creditPopupRoute: Routes = [
    {
        path: 'credit/:id/delete',
        component: CreditDeletePopupComponent,
        resolve: {
            credit: CreditResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Credits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
