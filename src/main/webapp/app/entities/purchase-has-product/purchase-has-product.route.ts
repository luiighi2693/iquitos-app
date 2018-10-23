import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PurchaseHasProduct } from 'app/shared/model/purchase-has-product.model';
import { PurchaseHasProductService } from './purchase-has-product.service';
import { PurchaseHasProductComponent } from './purchase-has-product.component';
import { PurchaseHasProductDetailComponent } from './purchase-has-product-detail.component';
import { PurchaseHasProductUpdateComponent } from './purchase-has-product-update.component';
import { PurchaseHasProductDeletePopupComponent } from './purchase-has-product-delete-dialog.component';
import { IPurchaseHasProduct } from 'app/shared/model/purchase-has-product.model';

@Injectable({ providedIn: 'root' })
export class PurchaseHasProductResolve implements Resolve<IPurchaseHasProduct> {
    constructor(private service: PurchaseHasProductService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((purchaseHasProduct: HttpResponse<PurchaseHasProduct>) => purchaseHasProduct.body));
        }
        return of(new PurchaseHasProduct());
    }
}

export const purchaseHasProductRoute: Routes = [
    {
        path: 'purchase-has-product',
        component: PurchaseHasProductComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PurchaseHasProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-has-product/:id/view',
        component: PurchaseHasProductDetailComponent,
        resolve: {
            purchaseHasProduct: PurchaseHasProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseHasProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-has-product/new',
        component: PurchaseHasProductUpdateComponent,
        resolve: {
            purchaseHasProduct: PurchaseHasProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseHasProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-has-product/:id/edit',
        component: PurchaseHasProductUpdateComponent,
        resolve: {
            purchaseHasProduct: PurchaseHasProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseHasProducts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseHasProductPopupRoute: Routes = [
    {
        path: 'purchase-has-product/:id/delete',
        component: PurchaseHasProductDeletePopupComponent,
        resolve: {
            purchaseHasProduct: PurchaseHasProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseHasProducts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
