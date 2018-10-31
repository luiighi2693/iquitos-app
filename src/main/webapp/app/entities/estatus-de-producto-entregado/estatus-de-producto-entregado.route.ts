import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EstatusDeProductoEntregado } from 'app/shared/model/estatus-de-producto-entregado.model';
import { EstatusDeProductoEntregadoService } from './estatus-de-producto-entregado.service';
import { EstatusDeProductoEntregadoComponent } from './estatus-de-producto-entregado.component';
import { EstatusDeProductoEntregadoDetailComponent } from './estatus-de-producto-entregado-detail.component';
import { EstatusDeProductoEntregadoUpdateComponent } from './estatus-de-producto-entregado-update.component';
import { EstatusDeProductoEntregadoDeletePopupComponent } from './estatus-de-producto-entregado-delete-dialog.component';
import { IEstatusDeProductoEntregado } from 'app/shared/model/estatus-de-producto-entregado.model';

@Injectable({ providedIn: 'root' })
export class EstatusDeProductoEntregadoResolve implements Resolve<IEstatusDeProductoEntregado> {
    constructor(private service: EstatusDeProductoEntregadoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((estatusDeProductoEntregado: HttpResponse<EstatusDeProductoEntregado>) => estatusDeProductoEntregado.body));
        }
        return of(new EstatusDeProductoEntregado());
    }
}

export const estatusDeProductoEntregadoRoute: Routes = [
    {
        path: 'estatus-de-producto-entregado',
        component: EstatusDeProductoEntregadoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstatusDeProductoEntregados'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estatus-de-producto-entregado/:id/view',
        component: EstatusDeProductoEntregadoDetailComponent,
        resolve: {
            estatusDeProductoEntregado: EstatusDeProductoEntregadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstatusDeProductoEntregados'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estatus-de-producto-entregado/new',
        component: EstatusDeProductoEntregadoUpdateComponent,
        resolve: {
            estatusDeProductoEntregado: EstatusDeProductoEntregadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstatusDeProductoEntregados'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estatus-de-producto-entregado/:id/edit',
        component: EstatusDeProductoEntregadoUpdateComponent,
        resolve: {
            estatusDeProductoEntregado: EstatusDeProductoEntregadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstatusDeProductoEntregados'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estatusDeProductoEntregadoPopupRoute: Routes = [
    {
        path: 'estatus-de-producto-entregado/:id/delete',
        component: EstatusDeProductoEntregadoDeletePopupComponent,
        resolve: {
            estatusDeProductoEntregado: EstatusDeProductoEntregadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstatusDeProductoEntregados'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
