import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProductsDeliveredStatus } from 'app/shared/model/products-delivered-status.model';
import { ProductsDeliveredStatusService } from './products-delivered-status.service';
import { ProductsDeliveredStatusComponent } from './products-delivered-status.component';
import { ProductsDeliveredStatusDetailComponent } from './products-delivered-status-detail.component';
import { ProductsDeliveredStatusUpdateComponent } from './products-delivered-status-update.component';
import { ProductsDeliveredStatusDeletePopupComponent } from './products-delivered-status-delete-dialog.component';
import { IProductsDeliveredStatus } from 'app/shared/model/products-delivered-status.model';

@Injectable({ providedIn: 'root' })
export class ProductsDeliveredStatusResolve implements Resolve<IProductsDeliveredStatus> {
    constructor(private service: ProductsDeliveredStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((productsDeliveredStatus: HttpResponse<ProductsDeliveredStatus>) => productsDeliveredStatus.body));
        }
        return of(new ProductsDeliveredStatus());
    }
}

export const productsDeliveredStatusRoute: Routes = [
    {
        path: 'products-delivered-status',
        component: ProductsDeliveredStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductsDeliveredStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'products-delivered-status/:id/view',
        component: ProductsDeliveredStatusDetailComponent,
        resolve: {
            productsDeliveredStatus: ProductsDeliveredStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductsDeliveredStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'products-delivered-status/new',
        component: ProductsDeliveredStatusUpdateComponent,
        resolve: {
            productsDeliveredStatus: ProductsDeliveredStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductsDeliveredStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'products-delivered-status/:id/edit',
        component: ProductsDeliveredStatusUpdateComponent,
        resolve: {
            productsDeliveredStatus: ProductsDeliveredStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductsDeliveredStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productsDeliveredStatusPopupRoute: Routes = [
    {
        path: 'products-delivered-status/:id/delete',
        component: ProductsDeliveredStatusDeletePopupComponent,
        resolve: {
            productsDeliveredStatus: ProductsDeliveredStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductsDeliveredStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
