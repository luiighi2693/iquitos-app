import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    AmortizacionComponent,
    AmortizacionDetailComponent,
    AmortizacionUpdateComponent,
    AmortizacionDeletePopupComponent,
    AmortizacionDeleteDialogComponent,
    amortizacionRoute,
    amortizacionPopupRoute
} from './';

const ENTITY_STATES = [...amortizacionRoute, ...amortizacionPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AmortizacionComponent,
        AmortizacionDetailComponent,
        AmortizacionUpdateComponent,
        AmortizacionDeleteDialogComponent,
        AmortizacionDeletePopupComponent
    ],
    entryComponents: [
        AmortizacionComponent,
        AmortizacionUpdateComponent,
        AmortizacionDeleteDialogComponent,
        AmortizacionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppAmortizacionModule {}
