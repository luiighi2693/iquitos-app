import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SellHasProduct } from 'app/shared/model/sell-has-product.model';
import { SellHasProductService } from './sell-has-product.service';
import { SellHasProductComponent } from './sell-has-product.component';
import { SellHasProductDetailComponent } from './sell-has-product-detail.component';
import { SellHasProductUpdateComponent } from './sell-has-product-update.component';
import { SellHasProductDeletePopupComponent } from './sell-has-product-delete-dialog.component';
import { ISellHasProduct } from 'app/shared/model/sell-has-product.model';

@Injectable({ providedIn: 'root' })
export class SellHasProductResolve implements Resolve<ISellHasProduct> {
    constructor(private service: SellHasProductService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sellHasProduct: HttpResponse<SellHasProduct>) => sellHasProduct.body));
        }
        return of(new SellHasProduct());
    }
}

export const sellHasProductRoute: Routes = [
    {
        path: 'sell-has-product',
        component: SellHasProductComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SellHasProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sell-has-product/:id/view',
        component: SellHasProductDetailComponent,
        resolve: {
            sellHasProduct: SellHasProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SellHasProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sell-has-product/new',
        component: SellHasProductUpdateComponent,
        resolve: {
            sellHasProduct: SellHasProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SellHasProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sell-has-product/:id/edit',
        component: SellHasProductUpdateComponent,
        resolve: {
            sellHasProduct: SellHasProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SellHasProducts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sellHasProductPopupRoute: Routes = [
    {
        path: 'sell-has-product/:id/delete',
        component: SellHasProductDeletePopupComponent,
        resolve: {
            sellHasProduct: SellHasProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SellHasProducts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
