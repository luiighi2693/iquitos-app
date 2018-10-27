import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ConceptOperationOutput } from 'app/shared/model/concept-operation-output.model';
import { ConceptOperationOutputService } from './concept-operation-output.service';
import { ConceptOperationOutputComponent } from './concept-operation-output.component';
import { ConceptOperationOutputDetailComponent } from './concept-operation-output-detail.component';
import { ConceptOperationOutputUpdateComponent } from './concept-operation-output-update.component';
import { ConceptOperationOutputDeletePopupComponent } from './concept-operation-output-delete-dialog.component';
import { IConceptOperationOutput } from 'app/shared/model/concept-operation-output.model';

@Injectable({ providedIn: 'root' })
export class ConceptOperationOutputResolve implements Resolve<IConceptOperationOutput> {
    constructor(private service: ConceptOperationOutputService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((conceptOperationOutput: HttpResponse<ConceptOperationOutput>) => conceptOperationOutput.body));
        }
        return of(new ConceptOperationOutput());
    }
}

export const conceptOperationOutputRoute: Routes = [
    {
        path: 'concept-operation-output',
        component: ConceptOperationOutputComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConceptOperationOutputs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'concept-operation-output/:id/view',
        component: ConceptOperationOutputDetailComponent,
        resolve: {
            conceptOperationOutput: ConceptOperationOutputResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConceptOperationOutputs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'concept-operation-output/new',
        component: ConceptOperationOutputUpdateComponent,
        resolve: {
            conceptOperationOutput: ConceptOperationOutputResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConceptOperationOutputs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'concept-operation-output/:id/edit',
        component: ConceptOperationOutputUpdateComponent,
        resolve: {
            conceptOperationOutput: ConceptOperationOutputResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConceptOperationOutputs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const conceptOperationOutputPopupRoute: Routes = [
    {
        path: 'concept-operation-output/:id/delete',
        component: ConceptOperationOutputDeletePopupComponent,
        resolve: {
            conceptOperationOutput: ConceptOperationOutputResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConceptOperationOutputs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
