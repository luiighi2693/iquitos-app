import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    PurchaseStatusComponent,
    PurchaseStatusDetailComponent,
    PurchaseStatusUpdateComponent,
    PurchaseStatusDeletePopupComponent,
    PurchaseStatusDeleteDialogComponent,
    purchaseStatusRoute,
    purchaseStatusPopupRoute
} from './';

const ENTITY_STATES = [...purchaseStatusRoute, ...purchaseStatusPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PurchaseStatusComponent,
        PurchaseStatusDetailComponent,
        PurchaseStatusUpdateComponent,
        PurchaseStatusDeleteDialogComponent,
        PurchaseStatusDeletePopupComponent
    ],
    entryComponents: [
        PurchaseStatusComponent,
        PurchaseStatusUpdateComponent,
        PurchaseStatusDeleteDialogComponent,
        PurchaseStatusDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppPurchaseStatusModule {}
