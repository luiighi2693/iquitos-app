import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';
import { TipoDeDocumentoDeVentaService } from './tipo-de-documento-de-venta.service';
import { TipoDeDocumentoDeVentaComponent } from './tipo-de-documento-de-venta.component';
import { TipoDeDocumentoDeVentaDetailComponent } from './tipo-de-documento-de-venta-detail.component';
import { TipoDeDocumentoDeVentaUpdateComponent } from './tipo-de-documento-de-venta-update.component';
import { TipoDeDocumentoDeVentaDeletePopupComponent } from './tipo-de-documento-de-venta-delete-dialog.component';
import { ITipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';

@Injectable({ providedIn: 'root' })
export class TipoDeDocumentoDeVentaResolve implements Resolve<ITipoDeDocumentoDeVenta> {
    constructor(private service: TipoDeDocumentoDeVentaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((tipoDeDocumentoDeVenta: HttpResponse<TipoDeDocumentoDeVenta>) => tipoDeDocumentoDeVenta.body));
        }
        return of(new TipoDeDocumentoDeVenta());
    }
}

export const tipoDeDocumentoDeVentaRoute: Routes = [
    {
        path: 'tipo-de-documento-de-venta',
        component: TipoDeDocumentoDeVentaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentoDeVentas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-documento-de-venta/:id/view',
        component: TipoDeDocumentoDeVentaDetailComponent,
        resolve: {
            tipoDeDocumentoDeVenta: TipoDeDocumentoDeVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentoDeVentas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-documento-de-venta/new',
        component: TipoDeDocumentoDeVentaUpdateComponent,
        resolve: {
            tipoDeDocumentoDeVenta: TipoDeDocumentoDeVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentoDeVentas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-documento-de-venta/:id/edit',
        component: TipoDeDocumentoDeVentaUpdateComponent,
        resolve: {
            tipoDeDocumentoDeVenta: TipoDeDocumentoDeVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentoDeVentas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoDeDocumentoDeVentaPopupRoute: Routes = [
    {
        path: 'tipo-de-documento-de-venta/:id/delete',
        component: TipoDeDocumentoDeVentaDeletePopupComponent,
        resolve: {
            tipoDeDocumentoDeVenta: TipoDeDocumentoDeVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentoDeVentas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
