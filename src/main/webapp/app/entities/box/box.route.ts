import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Box } from 'app/shared/model/box.model';
import { BoxService } from './box.service';
import { BoxComponent } from './box.component';
import { BoxDetailComponent } from './box-detail.component';
import { BoxUpdateComponent } from './box-update.component';
import { BoxDeletePopupComponent } from './box-delete-dialog.component';
import { IBox } from 'app/shared/model/box.model';

@Injectable({ providedIn: 'root' })
export class BoxResolve implements Resolve<IBox> {
    constructor(private service: BoxService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((box: HttpResponse<Box>) => box.body));
        }
        return of(new Box());
    }
}

export const boxRoute: Routes = [
    {
        path: 'box',
        component: BoxComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Boxes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'box/:id/view',
        component: BoxDetailComponent,
        resolve: {
            box: BoxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Boxes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'box/new',
        component: BoxUpdateComponent,
        resolve: {
            box: BoxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Boxes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'box/:id/edit',
        component: BoxUpdateComponent,
        resolve: {
            box: BoxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Boxes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const boxPopupRoute: Routes = [
    {
        path: 'box/:id/delete',
        component: BoxDeletePopupComponent,
        resolve: {
            box: BoxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Boxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
