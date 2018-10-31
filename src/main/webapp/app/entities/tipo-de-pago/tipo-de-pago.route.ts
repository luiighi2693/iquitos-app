import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TipoDePago } from 'app/shared/model/tipo-de-pago.model';
import { TipoDePagoService } from './tipo-de-pago.service';
import { TipoDePagoComponent } from './tipo-de-pago.component';
import { TipoDePagoDetailComponent } from './tipo-de-pago-detail.component';
import { TipoDePagoUpdateComponent } from './tipo-de-pago-update.component';
import { TipoDePagoDeletePopupComponent } from './tipo-de-pago-delete-dialog.component';
import { ITipoDePago } from 'app/shared/model/tipo-de-pago.model';

@Injectable({ providedIn: 'root' })
export class TipoDePagoResolve implements Resolve<ITipoDePago> {
    constructor(private service: TipoDePagoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((tipoDePago: HttpResponse<TipoDePago>) => tipoDePago.body));
        }
        return of(new TipoDePago());
    }
}

export const tipoDePagoRoute: Routes = [
    {
        path: 'tipo-de-pago',
        component: TipoDePagoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDePagos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-pago/:id/view',
        component: TipoDePagoDetailComponent,
        resolve: {
            tipoDePago: TipoDePagoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDePagos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-pago/new',
        component: TipoDePagoUpdateComponent,
        resolve: {
            tipoDePago: TipoDePagoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDePagos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-pago/:id/edit',
        component: TipoDePagoUpdateComponent,
        resolve: {
            tipoDePago: TipoDePagoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDePagos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoDePagoPopupRoute: Routes = [
    {
        path: 'tipo-de-pago/:id/delete',
        component: TipoDePagoDeletePopupComponent,
        resolve: {
            tipoDePago: TipoDePagoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoDePagos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
