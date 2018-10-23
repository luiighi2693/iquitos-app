import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UnitMeasurement } from 'app/shared/model/unit-measurement.model';
import { UnitMeasurementService } from './unit-measurement.service';
import { UnitMeasurementComponent } from './unit-measurement.component';
import { UnitMeasurementDetailComponent } from './unit-measurement-detail.component';
import { UnitMeasurementUpdateComponent } from './unit-measurement-update.component';
import { UnitMeasurementDeletePopupComponent } from './unit-measurement-delete-dialog.component';
import { IUnitMeasurement } from 'app/shared/model/unit-measurement.model';

@Injectable({ providedIn: 'root' })
export class UnitMeasurementResolve implements Resolve<IUnitMeasurement> {
    constructor(private service: UnitMeasurementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((unitMeasurement: HttpResponse<UnitMeasurement>) => unitMeasurement.body));
        }
        return of(new UnitMeasurement());
    }
}

export const unitMeasurementRoute: Routes = [
    {
        path: 'unit-measurement',
        component: UnitMeasurementComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'UnitMeasurements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit-measurement/:id/view',
        component: UnitMeasurementDetailComponent,
        resolve: {
            unitMeasurement: UnitMeasurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitMeasurements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit-measurement/new',
        component: UnitMeasurementUpdateComponent,
        resolve: {
            unitMeasurement: UnitMeasurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitMeasurements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit-measurement/:id/edit',
        component: UnitMeasurementUpdateComponent,
        resolve: {
            unitMeasurement: UnitMeasurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitMeasurements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const unitMeasurementPopupRoute: Routes = [
    {
        path: 'unit-measurement/:id/delete',
        component: UnitMeasurementDeletePopupComponent,
        resolve: {
            unitMeasurement: UnitMeasurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UnitMeasurements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
