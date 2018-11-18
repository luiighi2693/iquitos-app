import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';
import { CuentaProveedorService } from './cuenta-proveedor.service';
import { CuentaProveedorComponent } from './cuenta-proveedor.component';
import { CuentaProveedorDetailComponent } from './cuenta-proveedor-detail.component';
import { CuentaProveedorUpdateComponent } from './cuenta-proveedor-update.component';
import { CuentaProveedorDeletePopupComponent } from './cuenta-proveedor-delete-dialog.component';
import { ICuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';

@Injectable({ providedIn: 'root' })
export class CuentaProveedorResolve implements Resolve<ICuentaProveedor> {
    constructor(private service: CuentaProveedorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CuentaProveedor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CuentaProveedor>) => response.ok),
                map((cuentaProveedor: HttpResponse<CuentaProveedor>) => cuentaProveedor.body)
            );
        }
        return of(new CuentaProveedor());
    }
}

export const cuentaProveedorRoute: Routes = [
    {
        path: 'cuenta-proveedor',
        component: CuentaProveedorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CuentaProveedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cuenta-proveedor/:id/view',
        component: CuentaProveedorDetailComponent,
        resolve: {
            cuentaProveedor: CuentaProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CuentaProveedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cuenta-proveedor/new',
        component: CuentaProveedorUpdateComponent,
        resolve: {
            cuentaProveedor: CuentaProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CuentaProveedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cuenta-proveedor/:id/edit',
        component: CuentaProveedorUpdateComponent,
        resolve: {
            cuentaProveedor: CuentaProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CuentaProveedors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cuentaProveedorPopupRoute: Routes = [
    {
        path: 'cuenta-proveedor/:id/delete',
        component: CuentaProveedorDeletePopupComponent,
        resolve: {
            cuentaProveedor: CuentaProveedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CuentaProveedors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
