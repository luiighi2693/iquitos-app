import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProviderPayment } from 'app/shared/model/provider-payment.model';
import { ProviderPaymentService } from './provider-payment.service';
import { ProviderPaymentComponent } from './provider-payment.component';
import { ProviderPaymentDetailComponent } from './provider-payment-detail.component';
import { ProviderPaymentUpdateComponent } from './provider-payment-update.component';
import { ProviderPaymentDeletePopupComponent } from './provider-payment-delete-dialog.component';
import { IProviderPayment } from 'app/shared/model/provider-payment.model';

@Injectable({ providedIn: 'root' })
export class ProviderPaymentResolve implements Resolve<IProviderPayment> {
    constructor(private service: ProviderPaymentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((providerPayment: HttpResponse<ProviderPayment>) => providerPayment.body));
        }
        return of(new ProviderPayment());
    }
}

export const providerPaymentRoute: Routes = [
    {
        path: 'provider-payment',
        component: ProviderPaymentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProviderPayments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provider-payment/:id/view',
        component: ProviderPaymentDetailComponent,
        resolve: {
            providerPayment: ProviderPaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProviderPayments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provider-payment/new',
        component: ProviderPaymentUpdateComponent,
        resolve: {
            providerPayment: ProviderPaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProviderPayments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provider-payment/:id/edit',
        component: ProviderPaymentUpdateComponent,
        resolve: {
            providerPayment: ProviderPaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProviderPayments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const providerPaymentPopupRoute: Routes = [
    {
        path: 'provider-payment/:id/delete',
        component: ProviderPaymentDeletePopupComponent,
        resolve: {
            providerPayment: ProviderPaymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProviderPayments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
