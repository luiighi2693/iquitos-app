import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PagoDeProveedor } from 'app/shared/model/pago-de-proveedor.model';
import { PagoDeProveedorService } from './pago-de-proveedor.service';
import { PagoDeProveedorComponent } from './pago-de-proveedor.component';
import { PagoDeProveedorDetailComponent } from './pago-de-proveedor-detail.component';
import { PagoDeProveedorUpdateComponent } from './pago-de-proveedor-update.component';
import { PagoDeProveedorDeletePopupComponent } from './pago-de-proveedor-delete-dialog.component';
import { IPagoDeProveedor } from 'app/shared/model/pago-de-proveedor.model';

@Injectable({ providedIn: 'root' })
export class PagoDeProveedorResolve implements Resolve<IPagoDeProveedor> {
    constructor(private service: PagoDeProveedorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pagoDeProveedor: HttpResponse<PagoDeProveedor>) => pagoDeProveedor.body));
        }
        return of(new PagoDeProveedor());
    }
}

export const pagoDeProveedorRoute: Routes = [
    {
        path: 'pago-de-proveedor',
        component: PagoDeProveedorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PagoDeProveedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pago-de-proveedor/:id/view',
        component: PagoDeProveedorDetailComponent,
        resolve: {
            pagoDeProveedor: PagoDeProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PagoDeProveedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pago-de-proveedor/new',
        component: PagoDeProveedorUpdateComponent,
        resolve: {
            pagoDeProveedor: PagoDeProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PagoDeProveedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pago-de-proveedor/:id/edit',
        component: PagoDeProveedorUpdateComponent,
        resolve: {
            pagoDeProveedor: PagoDeProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PagoDeProveedors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pagoDeProveedorPopupRoute: Routes = [
    {
        path: 'pago-de-proveedor/:id/delete',
        component: PagoDeProveedorDeletePopupComponent,
        resolve: {
            pagoDeProveedor: PagoDeProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PagoDeProveedors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
