import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Credito } from 'app/shared/model/credito.model';
import { CreditoService } from './credito.service';
import { CreditoComponent } from './credito.component';
import { CreditoDetailComponent } from './credito-detail.component';
import { CreditoUpdateComponent } from './credito-update.component';
import { CreditoDeletePopupComponent } from './credito-delete-dialog.component';
import { ICredito } from 'app/shared/model/credito.model';

@Injectable({ providedIn: 'root' })
export class CreditoResolve implements Resolve<ICredito> {
    constructor(private service: CreditoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Credito> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Credito>) => response.ok),
                map((credito: HttpResponse<Credito>) => credito.body)
            );
        }
        return of(new Credito());
    }
}

export const creditoRoute: Routes = [
    {
        path: 'credito',
        component: CreditoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Creditos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'credito/:id/view',
        component: CreditoDetailComponent,
        resolve: {
            credito: CreditoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Creditos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'credito/new',
        component: CreditoUpdateComponent,
        resolve: {
            credito: CreditoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Creditos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'credito/:id/edit',
        component: CreditoUpdateComponent,
        resolve: {
            credito: CreditoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Creditos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creditoPopupRoute: Routes = [
    {
        path: 'credito/:id/delete',
        component: CreditoDeletePopupComponent,
        resolve: {
            credito: CreditoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Creditos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
