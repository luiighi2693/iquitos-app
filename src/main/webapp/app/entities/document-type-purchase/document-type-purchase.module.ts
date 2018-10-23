import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    DocumentTypePurchaseComponent,
    DocumentTypePurchaseDetailComponent,
    DocumentTypePurchaseUpdateComponent,
    DocumentTypePurchaseDeletePopupComponent,
    DocumentTypePurchaseDeleteDialogComponent,
    documentTypePurchaseRoute,
    documentTypePurchasePopupRoute
} from './';

const ENTITY_STATES = [...documentTypePurchaseRoute, ...documentTypePurchasePopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DocumentTypePurchaseComponent,
        DocumentTypePurchaseDetailComponent,
        DocumentTypePurchaseUpdateComponent,
        DocumentTypePurchaseDeleteDialogComponent,
        DocumentTypePurchaseDeletePopupComponent
    ],
    entryComponents: [
        DocumentTypePurchaseComponent,
        DocumentTypePurchaseUpdateComponent,
        DocumentTypePurchaseDeleteDialogComponent,
        DocumentTypePurchaseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppDocumentTypePurchaseModule {}
