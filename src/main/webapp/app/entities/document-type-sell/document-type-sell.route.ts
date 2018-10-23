import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DocumentTypeSell } from 'app/shared/model/document-type-sell.model';
import { DocumentTypeSellService } from './document-type-sell.service';
import { DocumentTypeSellComponent } from './document-type-sell.component';
import { DocumentTypeSellDetailComponent } from './document-type-sell-detail.component';
import { DocumentTypeSellUpdateComponent } from './document-type-sell-update.component';
import { DocumentTypeSellDeletePopupComponent } from './document-type-sell-delete-dialog.component';
import { IDocumentTypeSell } from 'app/shared/model/document-type-sell.model';

@Injectable({ providedIn: 'root' })
export class DocumentTypeSellResolve implements Resolve<IDocumentTypeSell> {
    constructor(private service: DocumentTypeSellService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((documentTypeSell: HttpResponse<DocumentTypeSell>) => documentTypeSell.body));
        }
        return of(new DocumentTypeSell());
    }
}

export const documentTypeSellRoute: Routes = [
    {
        path: 'document-type-sell',
        component: DocumentTypeSellComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'DocumentTypeSells'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-type-sell/:id/view',
        component: DocumentTypeSellDetailComponent,
        resolve: {
            documentTypeSell: DocumentTypeSellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypeSells'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-type-sell/new',
        component: DocumentTypeSellUpdateComponent,
        resolve: {
            documentTypeSell: DocumentTypeSellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypeSells'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-type-sell/:id/edit',
        component: DocumentTypeSellUpdateComponent,
        resolve: {
            documentTypeSell: DocumentTypeSellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypeSells'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentTypeSellPopupRoute: Routes = [
    {
        path: 'document-type-sell/:id/delete',
        component: DocumentTypeSellDeletePopupComponent,
        resolve: {
            documentTypeSell: DocumentTypeSellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypeSells'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
