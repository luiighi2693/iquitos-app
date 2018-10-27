import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PaymentType } from 'app/shared/model/payment-type.model';
import { PaymentTypeService } from './payment-type.service';
import { PaymentTypeComponent } from './payment-type.component';
import { PaymentTypeDetailComponent } from './payment-type-detail.component';
import { PaymentTypeUpdateComponent } from './payment-type-update.component';
import { PaymentTypeDeletePopupComponent } from './payment-type-delete-dialog.component';
import { IPaymentType } from 'app/shared/model/payment-type.model';

@Injectable({ providedIn: 'root' })
export class PaymentTypeResolve implements Resolve<IPaymentType> {
    constructor(private service: PaymentTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((paymentType: HttpResponse<PaymentType>) => paymentType.body));
        }
        return of(new PaymentType());
    }
}

export const paymentTypeRoute: Routes = [
    {
        path: 'payment-type',
        component: PaymentTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-type/:id/view',
        component: PaymentTypeDetailComponent,
        resolve: {
            paymentType: PaymentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-type/new',
        component: PaymentTypeUpdateComponent,
        resolve: {
            paymentType: PaymentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-type/:id/edit',
        component: PaymentTypeUpdateComponent,
        resolve: {
            paymentType: PaymentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentTypePopupRoute: Routes = [
    {
        path: 'payment-type/:id/delete',
        component: PaymentTypeDeletePopupComponent,
        resolve: {
            paymentType: PaymentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
