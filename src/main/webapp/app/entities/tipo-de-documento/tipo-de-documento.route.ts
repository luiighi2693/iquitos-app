import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoDeDocumento } from 'app/shared/model/tipo-de-documento.model';
import { TipoDeDocumentoService } from './tipo-de-documento.service';
import { TipoDeDocumentoComponent } from './tipo-de-documento.component';
import { TipoDeDocumentoDetailComponent } from './tipo-de-documento-detail.component';
import { TipoDeDocumentoUpdateComponent } from './tipo-de-documento-update.component';
import { TipoDeDocumentoDeletePopupComponent } from './tipo-de-documento-delete-dialog.component';
import { ITipoDeDocumento } from 'app/shared/model/tipo-de-documento.model';

@Injectable({ providedIn: 'root' })
export class TipoDeDocumentoResolve implements Resolve<ITipoDeDocumento> {
    constructor(private service: TipoDeDocumentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TipoDeDocumento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TipoDeDocumento>) => response.ok),
                map((tipoDeDocumento: HttpResponse<TipoDeDocumento>) => tipoDeDocumento.body)
            );
        }
        return of(new TipoDeDocumento());
    }
}

export const tipoDeDocumentoRoute: Routes = [
    {
        path: 'tipo-de-documento',
        component: TipoDeDocumentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-documento/:id/view',
        component: TipoDeDocumentoDetailComponent,
        resolve: {
            tipoDeDocumento: TipoDeDocumentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-documento/new',
        component: TipoDeDocumentoUpdateComponent,
        resolve: {
            tipoDeDocumento: TipoDeDocumentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-documento/:id/edit',
        component: TipoDeDocumentoUpdateComponent,
        resolve: {
            tipoDeDocumento: TipoDeDocumentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoDeDocumentoPopupRoute: Routes = [
    {
        path: 'tipo-de-documento/:id/delete',
        component: TipoDeDocumentoDeletePopupComponent,
        resolve: {
            tipoDeDocumento: TipoDeDocumentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDeDocumentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
