import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoDeOperacionDeIngreso } from 'app/shared/model/tipo-de-operacion-de-ingreso.model';
import { TipoDeOperacionDeIngresoService } from './tipo-de-operacion-de-ingreso.service';
import { TipoDeOperacionDeIngresoComponent } from './tipo-de-operacion-de-ingreso.component';
import { TipoDeOperacionDeIngresoDetailComponent } from './tipo-de-operacion-de-ingreso-detail.component';
import { TipoDeOperacionDeIngresoUpdateComponent } from './tipo-de-operacion-de-ingreso-update.component';
import { TipoDeOperacionDeIngresoDeletePopupComponent } from './tipo-de-operacion-de-ingreso-delete-dialog.component';
import { ITipoDeOperacionDeIngreso } from 'app/shared/model/tipo-de-operacion-de-ingreso.model';

@Injectable({ providedIn: 'root' })
export class TipoDeOperacionDeIngresoResolve implements Resolve<ITipoDeOperacionDeIngreso> {
    constructor(private service: TipoDeOperacionDeIngresoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TipoDeOperacionDeIngreso> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TipoDeOperacionDeIngreso>) => response.ok),
                map((tipoDeOperacionDeIngreso: HttpResponse<TipoDeOperacionDeIngreso>) => tipoDeOperacionDeIngreso.body)
            );
        }
        return of(new TipoDeOperacionDeIngreso());
    }
}

export const tipoDeOperacionDeIngresoRoute: Routes = [
    {
        path: 'tipo-de-operacion-de-ingreso',
        component: TipoDeOperacionDeIngresoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeOperacionDeIngresos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-operacion-de-ingreso/:id/view',
        component: TipoDeOperacionDeIngresoDetailComponent,
        resolve: {
            tipoDeOperacionDeIngreso: TipoDeOperacionDeIngresoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeOperacionDeIngresos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-operacion-de-ingreso/new',
        component: TipoDeOperacionDeIngresoUpdateComponent,
        resolve: {
            tipoDeOperacionDeIngreso: TipoDeOperacionDeIngresoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeOperacionDeIngresos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-operacion-de-ingreso/:id/edit',
        component: TipoDeOperacionDeIngresoUpdateComponent,
        resolve: {
            tipoDeOperacionDeIngreso: TipoDeOperacionDeIngresoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeOperacionDeIngresos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoDeOperacionDeIngresoPopupRoute: Routes = [
    {
        path: 'tipo-de-operacion-de-ingreso/:id/delete',
        component: TipoDeOperacionDeIngresoDeletePopupComponent,
        resolve: {
            tipoDeOperacionDeIngreso: TipoDeOperacionDeIngresoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeOperacionDeIngresos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
