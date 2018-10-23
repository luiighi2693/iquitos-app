import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    VariantComponent,
    VariantDetailComponent,
    VariantUpdateComponent,
    VariantDeletePopupComponent,
    VariantDeleteDialogComponent,
    variantRoute,
    variantPopupRoute
} from './';

const ENTITY_STATES = [...variantRoute, ...variantPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VariantComponent,
        VariantDetailComponent,
        VariantUpdateComponent,
        VariantDeleteDialogComponent,
        VariantDeletePopupComponent
    ],
    entryComponents: [VariantComponent, VariantUpdateComponent, VariantDeleteDialogComponent, VariantDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppVariantModule {}
