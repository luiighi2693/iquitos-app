import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Sell } from 'app/shared/model/sell.model';
import { SellService } from './sell.service';
import { SellComponent } from './sell.component';
import { SellDetailComponent } from './sell-detail.component';
import { SellUpdateComponent } from './sell-update.component';
import { SellDeletePopupComponent } from './sell-delete-dialog.component';
import { ISell } from 'app/shared/model/sell.model';

@Injectable({ providedIn: 'root' })
export class SellResolve implements Resolve<ISell> {
    constructor(private service: SellService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sell: HttpResponse<Sell>) => sell.body));
        }
        return of(new Sell());
    }
}

export const sellRoute: Routes = [
    {
        path: 'sell',
        component: SellComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Sells'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sell/:id/view',
        component: SellDetailComponent,
        resolve: {
            sell: SellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sells'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sell/new',
        component: SellUpdateComponent,
        resolve: {
            sell: SellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sells'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sell/:id/edit',
        component: SellUpdateComponent,
        resolve: {
            sell: SellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sells'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sellPopupRoute: Routes = [
    {
        path: 'sell/:id/delete',
        component: SellDeletePopupComponent,
        resolve: {
            sell: SellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sells'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
