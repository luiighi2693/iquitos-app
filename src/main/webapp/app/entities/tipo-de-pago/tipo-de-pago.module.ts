import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    TipoDePagoComponent,
    TipoDePagoDetailComponent,
    TipoDePagoUpdateComponent,
    TipoDePagoDeletePopupComponent,
    TipoDePagoDeleteDialogComponent,
    tipoDePagoRoute,
    tipoDePagoPopupRoute
} from './';

const ENTITY_STATES = [...tipoDePagoRoute, ...tipoDePagoPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoDePagoComponent,
        TipoDePagoDetailComponent,
        TipoDePagoUpdateComponent,
        TipoDePagoDeleteDialogComponent,
        TipoDePagoDeletePopupComponent
    ],
    entryComponents: [TipoDePagoComponent, TipoDePagoUpdateComponent, TipoDePagoDeleteDialogComponent, TipoDePagoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppTipoDePagoModule {}
