import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DocumentType } from 'app/shared/model/document-type.model';
import { DocumentTypeService } from './document-type.service';
import { DocumentTypeComponent } from './document-type.component';
import { DocumentTypeDetailComponent } from './document-type-detail.component';
import { DocumentTypeUpdateComponent } from './document-type-update.component';
import { DocumentTypeDeletePopupComponent } from './document-type-delete-dialog.component';
import { IDocumentType } from 'app/shared/model/document-type.model';

@Injectable({ providedIn: 'root' })
export class DocumentTypeResolve implements Resolve<IDocumentType> {
    constructor(private service: DocumentTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((documentType: HttpResponse<DocumentType>) => documentType.body));
        }
        return of(new DocumentType());
    }
}

export const documentTypeRoute: Routes = [
    {
        path: 'document-type',
        component: DocumentTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'DocumentTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-type/:id/view',
        component: DocumentTypeDetailComponent,
        resolve: {
            documentType: DocumentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-type/new',
        component: DocumentTypeUpdateComponent,
        resolve: {
            documentType: DocumentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-type/:id/edit',
        component: DocumentTypeUpdateComponent,
        resolve: {
            documentType: DocumentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentTypePopupRoute: Routes = [
    {
        path: 'document-type/:id/delete',
        component: DocumentTypeDeletePopupComponent,
        resolve: {
            documentType: DocumentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
