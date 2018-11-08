import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TipoDeOperacionDeGasto } from 'app/shared/model/tipo-de-operacion-de-gasto.model';
import { TipoDeOperacionDeGastoService } from './tipo-de-operacion-de-gasto.service';
import { TipoDeOperacionDeGastoComponent } from './tipo-de-operacion-de-gasto.component';
import { TipoDeOperacionDeGastoDetailComponent } from './tipo-de-operacion-de-gasto-detail.component';
import { TipoDeOperacionDeGastoUpdateComponent } from './tipo-de-operacion-de-gasto-update.component';
import { TipoDeOperacionDeGastoDeletePopupComponent } from './tipo-de-operacion-de-gasto-delete-dialog.component';
import { ITipoDeOperacionDeGasto } from 'app/shared/model/tipo-de-operacion-de-gasto.model';

@Injectable({ providedIn: 'root' })
export class TipoDeOperacionDeGastoResolve implements Resolve<ITipoDeOperacionDeGasto> {
    constructor(private service: TipoDeOperacionDeGastoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((tipoDeOperacionDeGasto: HttpResponse<TipoDeOperacionDeGasto>) => tipoDeOperacionDeGasto.body));
        }
        return of(new TipoDeOperacionDeGasto());
    }
}

export const tipoDeOperacionDeGastoRoute: Routes = [
    {
        path: 'tipo-de-operacion-de-gasto',
        component: TipoDeOperacionDeGastoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeOperacionDeGastos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-operacion-de-gasto/:id/view',
        component: TipoDeOperacionDeGastoDetailComponent,
        resolve: {
            tipoDeOperacionDeGasto: TipoDeOperacionDeGastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeOperacionDeGastos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-operacion-de-gasto/new',
        component: TipoDeOperacionDeGastoUpdateComponent,
        resolve: {
            tipoDeOperacionDeGasto: TipoDeOperacionDeGastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeOperacionDeGastos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-operacion-de-gasto/:id/edit',
        component: TipoDeOperacionDeGastoUpdateComponent,
        resolve: {
            tipoDeOperacionDeGasto: TipoDeOperacionDeGastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeOperacionDeGastos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoDeOperacionDeGastoPopupRoute: Routes = [
    {
        path: 'tipo-de-operacion-de-gasto/:id/delete',
        component: TipoDeOperacionDeGastoDeletePopupComponent,
        resolve: {
            tipoDeOperacionDeGasto: TipoDeOperacionDeGastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeOperacionDeGastos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
