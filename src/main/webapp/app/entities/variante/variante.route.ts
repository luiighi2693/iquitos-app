import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Variante } from 'app/shared/model/variante.model';
import { VarianteService } from './variante.service';
import { VarianteComponent } from './variante.component';
import { VarianteDetailComponent } from './variante-detail.component';
import { VarianteUpdateComponent } from './variante-update.component';
import { VarianteDeletePopupComponent } from './variante-delete-dialog.component';
import { IVariante } from 'app/shared/model/variante.model';

@Injectable({ providedIn: 'root' })
export class VarianteResolve implements Resolve<IVariante> {
    constructor(private service: VarianteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((variante: HttpResponse<Variante>) => variante.body));
        }
        return of(new Variante());
    }
}

export const varianteRoute: Routes = [
    {
        path: 'variante',
        component: VarianteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'variante/:id/view',
        component: VarianteDetailComponent,
        resolve: {
            variante: VarianteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'variante/new',
        component: VarianteUpdateComponent,
        resolve: {
            variante: VarianteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'variante/:id/edit',
        component: VarianteUpdateComponent,
        resolve: {
            variante: VarianteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variantes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const variantePopupRoute: Routes = [
    {
        path: 'variante/:id/delete',
        component: VarianteDeletePopupComponent,
        resolve: {
            variante: VarianteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variantes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
