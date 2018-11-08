import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    CreditoComponent,
    CreditoDetailComponent,
    CreditoUpdateComponent,
    CreditoDeletePopupComponent,
    CreditoDeleteDialogComponent,
    creditoRoute,
    creditoPopupRoute
} from './';

const ENTITY_STATES = [...creditoRoute, ...creditoPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CreditoComponent,
        CreditoDetailComponent,
        CreditoUpdateComponent,
        CreditoDeleteDialogComponent,
        CreditoDeletePopupComponent
    ],
    entryComponents: [CreditoComponent, CreditoUpdateComponent, CreditoDeleteDialogComponent, CreditoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppCreditoModule {}
