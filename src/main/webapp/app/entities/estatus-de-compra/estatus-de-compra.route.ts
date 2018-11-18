import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstatusDeCompra } from 'app/shared/model/estatus-de-compra.model';
import { EstatusDeCompraService } from './estatus-de-compra.service';
import { EstatusDeCompraComponent } from './estatus-de-compra.component';
import { EstatusDeCompraDetailComponent } from './estatus-de-compra-detail.component';
import { EstatusDeCompraUpdateComponent } from './estatus-de-compra-update.component';
import { EstatusDeCompraDeletePopupComponent } from './estatus-de-compra-delete-dialog.component';
import { IEstatusDeCompra } from 'app/shared/model/estatus-de-compra.model';

@Injectable({ providedIn: 'root' })
export class EstatusDeCompraResolve implements Resolve<IEstatusDeCompra> {
    constructor(private service: EstatusDeCompraService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<EstatusDeCompra> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EstatusDeCompra>) => response.ok),
                map((estatusDeCompra: HttpResponse<EstatusDeCompra>) => estatusDeCompra.body)
            );
        }
        return of(new EstatusDeCompra());
    }
}

export const estatusDeCompraRoute: Routes = [
    {
        path: 'estatus-de-compra',
        component: EstatusDeCompraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstatusDeCompras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estatus-de-compra/:id/view',
        component: EstatusDeCompraDetailComponent,
        resolve: {
            estatusDeCompra: EstatusDeCompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstatusDeCompras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estatus-de-compra/new',
        component: EstatusDeCompraUpdateComponent,
        resolve: {
            estatusDeCompra: EstatusDeCompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstatusDeCompras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estatus-de-compra/:id/edit',
        component: EstatusDeCompraUpdateComponent,
        resolve: {
            estatusDeCompra: EstatusDeCompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstatusDeCompras'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estatusDeCompraPopupRoute: Routes = [
    {
        path: 'estatus-de-compra/:id/delete',
        component: EstatusDeCompraDeletePopupComponent,
        resolve: {
            estatusDeCompra: EstatusDeCompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstatusDeCompras'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
