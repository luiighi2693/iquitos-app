import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    AmortizationComponent,
    AmortizationDetailComponent,
    AmortizationUpdateComponent,
    AmortizationDeletePopupComponent,
    AmortizationDeleteDialogComponent,
    amortizationRoute,
    amortizationPopupRoute
} from './';

const ENTITY_STATES = [...amortizationRoute, ...amortizationPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AmortizationComponent,
        AmortizationDetailComponent,
        AmortizationUpdateComponent,
        AmortizationDeleteDialogComponent,
        AmortizationDeletePopupComponent
    ],
    entryComponents: [
        AmortizationComponent,
        AmortizationUpdateComponent,
        AmortizationDeleteDialogComponent,
        AmortizationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppAmortizationModule {}
