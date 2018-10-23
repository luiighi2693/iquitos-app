import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    DocumentTypeSellComponent,
    DocumentTypeSellDetailComponent,
    DocumentTypeSellUpdateComponent,
    DocumentTypeSellDeletePopupComponent,
    DocumentTypeSellDeleteDialogComponent,
    documentTypeSellRoute,
    documentTypeSellPopupRoute
} from './';

const ENTITY_STATES = [...documentTypeSellRoute, ...documentTypeSellPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DocumentTypeSellComponent,
        DocumentTypeSellDetailComponent,
        DocumentTypeSellUpdateComponent,
        DocumentTypeSellDeleteDialogComponent,
        DocumentTypeSellDeletePopupComponent
    ],
    entryComponents: [
        DocumentTypeSellComponent,
        DocumentTypeSellUpdateComponent,
        DocumentTypeSellDeleteDialogComponent,
        DocumentTypeSellDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppDocumentTypeSellModule {}
