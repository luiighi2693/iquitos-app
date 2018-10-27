import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OrderProduct } from 'app/shared/model/order-product.model';
import { OrderProductService } from './order-product.service';
import { OrderProductComponent } from './order-product.component';
import { OrderProductDetailComponent } from './order-product-detail.component';
import { OrderProductUpdateComponent } from './order-product-update.component';
import { OrderProductDeletePopupComponent } from './order-product-delete-dialog.component';
import { IOrderProduct } from 'app/shared/model/order-product.model';

@Injectable({ providedIn: 'root' })
export class OrderProductResolve implements Resolve<IOrderProduct> {
    constructor(private service: OrderProductService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((orderProduct: HttpResponse<OrderProduct>) => orderProduct.body));
        }
        return of(new OrderProduct());
    }
}

export const orderProductRoute: Routes = [
    {
        path: 'order-product',
        component: OrderProductComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'OrderProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-product/:id/view',
        component: OrderProductDetailComponent,
        resolve: {
            orderProduct: OrderProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-product/new',
        component: OrderProductUpdateComponent,
        resolve: {
            orderProduct: OrderProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-product/:id/edit',
        component: OrderProductUpdateComponent,
        resolve: {
            orderProduct: OrderProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderProducts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderProductPopupRoute: Routes = [
    {
        path: 'order-product/:id/delete',
        component: OrderProductDeletePopupComponent,
        resolve: {
            orderProduct: OrderProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderProducts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
