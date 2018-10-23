import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Amortization } from 'app/shared/model/amortization.model';
import { AmortizationService } from './amortization.service';
import { AmortizationComponent } from './amortization.component';
import { AmortizationDetailComponent } from './amortization-detail.component';
import { AmortizationUpdateComponent } from './amortization-update.component';
import { AmortizationDeletePopupComponent } from './amortization-delete-dialog.component';
import { IAmortization } from 'app/shared/model/amortization.model';

@Injectable({ providedIn: 'root' })
export class AmortizationResolve implements Resolve<IAmortization> {
    constructor(private service: AmortizationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((amortization: HttpResponse<Amortization>) => amortization.body));
        }
        return of(new Amortization());
    }
}

export const amortizationRoute: Routes = [
    {
        path: 'amortization',
        component: AmortizationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Amortizations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amortization/:id/view',
        component: AmortizationDetailComponent,
        resolve: {
            amortization: AmortizationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amortizations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amortization/new',
        component: AmortizationUpdateComponent,
        resolve: {
            amortization: AmortizationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amortizations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amortization/:id/edit',
        component: AmortizationUpdateComponent,
        resolve: {
            amortization: AmortizationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amortizations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const amortizationPopupRoute: Routes = [
    {
        path: 'amortization/:id/delete',
        component: AmortizationDeletePopupComponent,
        resolve: {
            amortization: AmortizationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Amortizations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
