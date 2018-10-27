import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DocumentTypePurchase } from 'app/shared/model/document-type-purchase.model';
import { DocumentTypePurchaseService } from './document-type-purchase.service';
import { DocumentTypePurchaseComponent } from './document-type-purchase.component';
import { DocumentTypePurchaseDetailComponent } from './document-type-purchase-detail.component';
import { DocumentTypePurchaseUpdateComponent } from './document-type-purchase-update.component';
import { DocumentTypePurchaseDeletePopupComponent } from './document-type-purchase-delete-dialog.component';
import { IDocumentTypePurchase } from 'app/shared/model/document-type-purchase.model';

@Injectable({ providedIn: 'root' })
export class DocumentTypePurchaseResolve implements Resolve<IDocumentTypePurchase> {
    constructor(private service: DocumentTypePurchaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((documentTypePurchase: HttpResponse<DocumentTypePurchase>) => documentTypePurchase.body));
        }
        return of(new DocumentTypePurchase());
    }
}

export const documentTypePurchaseRoute: Routes = [
    {
        path: 'document-type-purchase',
        component: DocumentTypePurchaseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypePurchases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-type-purchase/:id/view',
        component: DocumentTypePurchaseDetailComponent,
        resolve: {
            documentTypePurchase: DocumentTypePurchaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypePurchases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-type-purchase/new',
        component: DocumentTypePurchaseUpdateComponent,
        resolve: {
            documentTypePurchase: DocumentTypePurchaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypePurchases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-type-purchase/:id/edit',
        component: DocumentTypePurchaseUpdateComponent,
        resolve: {
            documentTypePurchase: DocumentTypePurchaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypePurchases'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentTypePurchasePopupRoute: Routes = [
    {
        path: 'document-type-purchase/:id/delete',
        component: DocumentTypePurchaseDeletePopupComponent,
        resolve: {
            documentTypePurchase: DocumentTypePurchaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTypePurchases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
