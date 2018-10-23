import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    PurchaseHasProductComponent,
    PurchaseHasProductDetailComponent,
    PurchaseHasProductUpdateComponent,
    PurchaseHasProductDeletePopupComponent,
    PurchaseHasProductDeleteDialogComponent,
    purchaseHasProductRoute,
    purchaseHasProductPopupRoute
} from './';

const ENTITY_STATES = [...purchaseHasProductRoute, ...purchaseHasProductPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PurchaseHasProductComponent,
        PurchaseHasProductDetailComponent,
        PurchaseHasProductUpdateComponent,
        PurchaseHasProductDeleteDialogComponent,
        PurchaseHasProductDeletePopupComponent
    ],
    entryComponents: [
        PurchaseHasProductComponent,
        PurchaseHasProductUpdateComponent,
        PurchaseHasProductDeleteDialogComponent,
        PurchaseHasProductDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppPurchaseHasProductModule {}
