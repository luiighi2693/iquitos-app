import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoDetalle } from 'app/shared/model/producto-detalle.model';
import { ProductoDetalleService } from './producto-detalle.service';
import { ProductoDetalleComponent } from './producto-detalle.component';
import { ProductoDetalleDetailComponent } from './producto-detalle-detail.component';
import { ProductoDetalleUpdateComponent } from './producto-detalle-update.component';
import { ProductoDetalleDeletePopupComponent } from './producto-detalle-delete-dialog.component';
import { IProductoDetalle } from 'app/shared/model/producto-detalle.model';

@Injectable({ providedIn: 'root' })
export class ProductoDetalleResolve implements Resolve<IProductoDetalle> {
    constructor(private service: ProductoDetalleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProductoDetalle> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductoDetalle>) => response.ok),
                map((productoDetalle: HttpResponse<ProductoDetalle>) => productoDetalle.body)
            );
        }
        return of(new ProductoDetalle());
    }
}

export const productoDetalleRoute: Routes = [
    {
        path: 'producto-detalle',
        component: ProductoDetalleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductoDetalles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'producto-detalle/:id/view',
        component: ProductoDetalleDetailComponent,
        resolve: {
            productoDetalle: ProductoDetalleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductoDetalles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'producto-detalle/new',
        component: ProductoDetalleUpdateComponent,
        resolve: {
            productoDetalle: ProductoDetalleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductoDetalles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'producto-detalle/:id/edit',
        component: ProductoDetalleUpdateComponent,
        resolve: {
            productoDetalle: ProductoDetalleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductoDetalles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productoDetallePopupRoute: Routes = [
    {
        path: 'producto-detalle/:id/delete',
        component: ProductoDetalleDeletePopupComponent,
        resolve: {
            productoDetalle: ProductoDetalleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductoDetalles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
