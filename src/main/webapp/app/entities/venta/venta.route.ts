import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Venta } from 'app/shared/model/venta.model';
import { VentaService } from './venta.service';
import { VentaComponent } from './venta.component';
import { VentaDetailComponent } from './venta-detail.component';
import { VentaUpdateComponent } from './venta-update.component';
import { VentaDeletePopupComponent } from './venta-delete-dialog.component';
import { IVenta } from 'app/shared/model/venta.model';

@Injectable({ providedIn: 'root' })
export class VentaResolve implements Resolve<IVenta> {
    constructor(private service: VentaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((venta: HttpResponse<Venta>) => venta.body));
        }
        return of(new Venta());
    }
}

export const ventaRoute: Routes = [
    {
        path: 'venta',
        component: VentaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ventas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'venta/:id/view',
        component: VentaDetailComponent,
        resolve: {
            venta: VentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ventas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'venta/new',
        component: VentaUpdateComponent,
        resolve: {
            venta: VentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ventas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'venta/:id/edit',
        component: VentaUpdateComponent,
        resolve: {
            venta: VentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ventas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ventaPopupRoute: Routes = [
    {
        path: 'venta/:id/delete',
        component: VentaDeletePopupComponent,
        resolve: {
            venta: VentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ventas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
