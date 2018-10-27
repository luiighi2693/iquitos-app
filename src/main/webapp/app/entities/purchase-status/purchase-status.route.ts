import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PurchaseStatus } from 'app/shared/model/purchase-status.model';
import { PurchaseStatusService } from './purchase-status.service';
import { PurchaseStatusComponent } from './purchase-status.component';
import { PurchaseStatusDetailComponent } from './purchase-status-detail.component';
import { PurchaseStatusUpdateComponent } from './purchase-status-update.component';
import { PurchaseStatusDeletePopupComponent } from './purchase-status-delete-dialog.component';
import { IPurchaseStatus } from 'app/shared/model/purchase-status.model';

@Injectable({ providedIn: 'root' })
export class PurchaseStatusResolve implements Resolve<IPurchaseStatus> {
    constructor(private service: PurchaseStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((purchaseStatus: HttpResponse<PurchaseStatus>) => purchaseStatus.body));
        }
        return of(new PurchaseStatus());
    }
}

export const purchaseStatusRoute: Routes = [
    {
        path: 'purchase-status',
        component: PurchaseStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-status/:id/view',
        component: PurchaseStatusDetailComponent,
        resolve: {
            purchaseStatus: PurchaseStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-status/new',
        component: PurchaseStatusUpdateComponent,
        resolve: {
            purchaseStatus: PurchaseStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-status/:id/edit',
        component: PurchaseStatusUpdateComponent,
        resolve: {
            purchaseStatus: PurchaseStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseStatusPopupRoute: Routes = [
    {
        path: 'purchase-status/:id/delete',
        component: PurchaseStatusDeletePopupComponent,
        resolve: {
            purchaseStatus: PurchaseStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
