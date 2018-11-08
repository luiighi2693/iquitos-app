import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Amortizacion } from 'app/shared/model/amortizacion.model';
import { AmortizacionService } from './amortizacion.service';
import { AmortizacionComponent } from './amortizacion.component';
import { AmortizacionDetailComponent } from './amortizacion-detail.component';
import { AmortizacionUpdateComponent } from './amortizacion-update.component';
import { AmortizacionDeletePopupComponent } from './amortizacion-delete-dialog.component';
import { IAmortizacion } from 'app/shared/model/amortizacion.model';

@Injectable({ providedIn: 'root' })
export class AmortizacionResolve implements Resolve<IAmortizacion> {
    constructor(private service: AmortizacionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((amortizacion: HttpResponse<Amortizacion>) => amortizacion.body));
        }
        return of(new Amortizacion());
    }
}

export const amortizacionRoute: Routes = [
    {
        path: 'amortizacion',
        component: AmortizacionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amortizacions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amortizacion/:id/view',
        component: AmortizacionDetailComponent,
        resolve: {
            amortizacion: AmortizacionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amortizacions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amortizacion/new',
        component: AmortizacionUpdateComponent,
        resolve: {
            amortizacion: AmortizacionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amortizacions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amortizacion/:id/edit',
        component: AmortizacionUpdateComponent,
        resolve: {
            amortizacion: AmortizacionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amortizacions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const amortizacionPopupRoute: Routes = [
    {
        path: 'amortizacion/:id/delete',
        component: AmortizacionDeletePopupComponent,
        resolve: {
            amortizacion: AmortizacionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amortizacions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
