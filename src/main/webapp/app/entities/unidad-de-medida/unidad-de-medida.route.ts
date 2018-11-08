import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UnidadDeMedida } from 'app/shared/model/unidad-de-medida.model';
import { UnidadDeMedidaService } from './unidad-de-medida.service';
import { UnidadDeMedidaComponent } from './unidad-de-medida.component';
import { UnidadDeMedidaDetailComponent } from './unidad-de-medida-detail.component';
import { UnidadDeMedidaUpdateComponent } from './unidad-de-medida-update.component';
import { UnidadDeMedidaDeletePopupComponent } from './unidad-de-medida-delete-dialog.component';
import { IUnidadDeMedida } from 'app/shared/model/unidad-de-medida.model';

@Injectable({ providedIn: 'root' })
export class UnidadDeMedidaResolve implements Resolve<IUnidadDeMedida> {
    constructor(private service: UnidadDeMedidaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((unidadDeMedida: HttpResponse<UnidadDeMedida>) => unidadDeMedida.body));
        }
        return of(new UnidadDeMedida());
    }
}

export const unidadDeMedidaRoute: Routes = [
    {
        path: 'unidad-de-medida',
        component: UnidadDeMedidaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnidadDeMedidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unidad-de-medida/:id/view',
        component: UnidadDeMedidaDetailComponent,
        resolve: {
            unidadDeMedida: UnidadDeMedidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnidadDeMedidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unidad-de-medida/new',
        component: UnidadDeMedidaUpdateComponent,
        resolve: {
            unidadDeMedida: UnidadDeMedidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnidadDeMedidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unidad-de-medida/:id/edit',
        component: UnidadDeMedidaUpdateComponent,
        resolve: {
            unidadDeMedida: UnidadDeMedidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnidadDeMedidas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const unidadDeMedidaPopupRoute: Routes = [
    {
        path: 'unidad-de-medida/:id/delete',
        component: UnidadDeMedidaDeletePopupComponent,
        resolve: {
            unidadDeMedida: UnidadDeMedidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnidadDeMedidas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
