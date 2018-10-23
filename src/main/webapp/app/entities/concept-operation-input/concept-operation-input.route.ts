import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ConceptOperationInput } from 'app/shared/model/concept-operation-input.model';
import { ConceptOperationInputService } from './concept-operation-input.service';
import { ConceptOperationInputComponent } from './concept-operation-input.component';
import { ConceptOperationInputDetailComponent } from './concept-operation-input-detail.component';
import { ConceptOperationInputUpdateComponent } from './concept-operation-input-update.component';
import { ConceptOperationInputDeletePopupComponent } from './concept-operation-input-delete-dialog.component';
import { IConceptOperationInput } from 'app/shared/model/concept-operation-input.model';

@Injectable({ providedIn: 'root' })
export class ConceptOperationInputResolve implements Resolve<IConceptOperationInput> {
    constructor(private service: ConceptOperationInputService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((conceptOperationInput: HttpResponse<ConceptOperationInput>) => conceptOperationInput.body));
        }
        return of(new ConceptOperationInput());
    }
}

export const conceptOperationInputRoute: Routes = [
    {
        path: 'concept-operation-input',
        component: ConceptOperationInputComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ConceptOperationInputs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'concept-operation-input/:id/view',
        component: ConceptOperationInputDetailComponent,
        resolve: {
            conceptOperationInput: ConceptOperationInputResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConceptOperationInputs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'concept-operation-input/new',
        component: ConceptOperationInputUpdateComponent,
        resolve: {
            conceptOperationInput: ConceptOperationInputResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConceptOperationInputs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'concept-operation-input/:id/edit',
        component: ConceptOperationInputUpdateComponent,
        resolve: {
            conceptOperationInput: ConceptOperationInputResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConceptOperationInputs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const conceptOperationInputPopupRoute: Routes = [
    {
        path: 'concept-operation-input/:id/delete',
        component: ConceptOperationInputDeletePopupComponent,
        resolve: {
            conceptOperationInput: ConceptOperationInputResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConceptOperationInputs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
