import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    PurchaseComponent,
    PurchaseDetailComponent,
    PurchaseUpdateComponent,
    PurchaseDeletePopupComponent,
    PurchaseDeleteDialogComponent,
    purchaseRoute,
    purchasePopupRoute
} from './';

const ENTITY_STATES = [...purchaseRoute, ...purchasePopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PurchaseComponent,
        PurchaseDetailComponent,
        PurchaseUpdateComponent,
        PurchaseDeleteDialogComponent,
        PurchaseDeletePopupComponent
    ],
    entryComponents: [PurchaseComponent, PurchaseUpdateComponent, PurchaseDeleteDialogComponent, PurchaseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppPurchaseModule {}
