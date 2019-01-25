import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ParametroSistema } from 'app/shared/model/parametro-sistema.model';
import { ParametroSistemaService } from './parametro-sistema.service';
import { ParametroSistemaComponent } from './parametro-sistema.component';
import { ParametroSistemaDetailComponent } from './parametro-sistema-detail.component';
import { ParametroSistemaUpdateComponent } from './parametro-sistema-update.component';
import { ParametroSistemaDeletePopupComponent } from './parametro-sistema-delete-dialog.component';
import { IParametroSistema } from 'app/shared/model/parametro-sistema.model';

@Injectable({ providedIn: 'root' })
export class ParametroSistemaResolve implements Resolve<IParametroSistema> {
    constructor(private service: ParametroSistemaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ParametroSistema> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ParametroSistema>) => response.ok),
                map((parametroSistema: HttpResponse<ParametroSistema>) => parametroSistema.body)
            );
        }
        return of(new ParametroSistema());
    }
}

export const parametroSistemaRoute: Routes = [
    {
        path: 'parametro-sistema',
        component: ParametroSistemaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParametroSistemas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parametro-sistema/:id/view',
        component: ParametroSistemaDetailComponent,
        resolve: {
            parametroSistema: ParametroSistemaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParametroSistemas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parametro-sistema/new',
        component: ParametroSistemaUpdateComponent,
        resolve: {
            parametroSistema: ParametroSistemaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParametroSistemas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parametro-sistema/:id/edit',
        component: ParametroSistemaUpdateComponent,
        resolve: {
            parametroSistema: ParametroSistemaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParametroSistemas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parametroSistemaPopupRoute: Routes = [
    {
        path: 'parametro-sistema/:id/delete',
        component: ParametroSistemaDeletePopupComponent,
        resolve: {
            parametroSistema: ParametroSistemaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParametroSistemas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
