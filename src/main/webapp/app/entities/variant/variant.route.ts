import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Variant } from 'app/shared/model/variant.model';
import { VariantService } from './variant.service';
import { VariantComponent } from './variant.component';
import { VariantDetailComponent } from './variant-detail.component';
import { VariantUpdateComponent } from './variant-update.component';
import { VariantDeletePopupComponent } from './variant-delete-dialog.component';
import { IVariant } from 'app/shared/model/variant.model';

@Injectable({ providedIn: 'root' })
export class VariantResolve implements Resolve<IVariant> {
    constructor(private service: VariantService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((variant: HttpResponse<Variant>) => variant.body));
        }
        return of(new Variant());
    }
}

export const variantRoute: Routes = [
    {
        path: 'variant',
        component: VariantComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Variants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'variant/:id/view',
        component: VariantDetailComponent,
        resolve: {
            variant: VariantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'variant/new',
        component: VariantUpdateComponent,
        resolve: {
            variant: VariantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'variant/:id/edit',
        component: VariantUpdateComponent,
        resolve: {
            variant: VariantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const variantPopupRoute: Routes = [
    {
        path: 'variant/:id/delete',
        component: VariantDeletePopupComponent,
        resolve: {
            variant: VariantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
