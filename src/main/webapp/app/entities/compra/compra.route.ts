import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Compra } from 'app/shared/model/compra.model';
import { CompraService } from './compra.service';
import { CompraComponent } from './compra.component';
import { CompraDetailComponent } from './compra-detail.component';
import { CompraUpdateComponent } from './compra-update.component';
import { CompraDeletePopupComponent } from './compra-delete-dialog.component';
import { ICompra } from 'app/shared/model/compra.model';

@Injectable({ providedIn: 'root' })
export class CompraResolve implements Resolve<ICompra> {
    constructor(private service: CompraService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((compra: HttpResponse<Compra>) => compra.body));
        }
        return of(new Compra());
    }
}

export const compraRoute: Routes = [
    {
        path: 'compra',
        component: CompraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Compras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compra/:id/view',
        component: CompraDetailComponent,
        resolve: {
            compra: CompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Compras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compra/new',
        component: CompraUpdateComponent,
        resolve: {
            compra: CompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Compras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compra/:id/edit',
        component: CompraUpdateComponent,
        resolve: {
            compra: CompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Compras'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const compraPopupRoute: Routes = [
    {
        path: 'compra/:id/delete',
        component: CompraDeletePopupComponent,
        resolve: {
            compra: CompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Compras'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
