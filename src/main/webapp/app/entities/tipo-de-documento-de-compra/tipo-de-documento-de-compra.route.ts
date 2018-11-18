import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';
import { TipoDeDocumentoDeCompraService } from './tipo-de-documento-de-compra.service';
import { TipoDeDocumentoDeCompraComponent } from './tipo-de-documento-de-compra.component';
import { TipoDeDocumentoDeCompraDetailComponent } from './tipo-de-documento-de-compra-detail.component';
import { TipoDeDocumentoDeCompraUpdateComponent } from './tipo-de-documento-de-compra-update.component';
import { TipoDeDocumentoDeCompraDeletePopupComponent } from './tipo-de-documento-de-compra-delete-dialog.component';
import { ITipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';

@Injectable({ providedIn: 'root' })
export class TipoDeDocumentoDeCompraResolve implements Resolve<ITipoDeDocumentoDeCompra> {
    constructor(private service: TipoDeDocumentoDeCompraService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TipoDeDocumentoDeCompra> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TipoDeDocumentoDeCompra>) => response.ok),
                map((tipoDeDocumentoDeCompra: HttpResponse<TipoDeDocumentoDeCompra>) => tipoDeDocumentoDeCompra.body)
            );
        }
        return of(new TipoDeDocumentoDeCompra());
    }
}

export const tipoDeDocumentoDeCompraRoute: Routes = [
    {
        path: 'tipo-de-documento-de-compra',
        component: TipoDeDocumentoDeCompraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentoDeCompras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-documento-de-compra/:id/view',
        component: TipoDeDocumentoDeCompraDetailComponent,
        resolve: {
            tipoDeDocumentoDeCompra: TipoDeDocumentoDeCompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentoDeCompras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-documento-de-compra/new',
        component: TipoDeDocumentoDeCompraUpdateComponent,
        resolve: {
            tipoDeDocumentoDeCompra: TipoDeDocumentoDeCompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentoDeCompras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-documento-de-compra/:id/edit',
        component: TipoDeDocumentoDeCompraUpdateComponent,
        resolve: {
            tipoDeDocumentoDeCompra: TipoDeDocumentoDeCompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentoDeCompras'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoDeDocumentoDeCompraPopupRoute: Routes = [
    {
        path: 'tipo-de-documento-de-compra/:id/delete',
        component: TipoDeDocumentoDeCompraDeletePopupComponent,
        resolve: {
            tipoDeDocumentoDeCompra: TipoDeDocumentoDeCompraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentoDeCompras'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
