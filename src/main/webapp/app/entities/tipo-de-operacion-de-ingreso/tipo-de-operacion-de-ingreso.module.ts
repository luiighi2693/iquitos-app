import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    TipoDeOperacionDeIngresoComponent,
    TipoDeOperacionDeIngresoDetailComponent,
    TipoDeOperacionDeIngresoUpdateComponent,
    TipoDeOperacionDeIngresoDeletePopupComponent,
    TipoDeOperacionDeIngresoDeleteDialogComponent,
    tipoDeOperacionDeIngresoRoute,
    tipoDeOperacionDeIngresoPopupRoute
} from './';

const ENTITY_STATES = [...tipoDeOperacionDeIngresoRoute, ...tipoDeOperacionDeIngresoPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoDeOperacionDeIngresoComponent,
        TipoDeOperacionDeIngresoDetailComponent,
        TipoDeOperacionDeIngresoUpdateComponent,
        TipoDeOperacionDeIngresoDeleteDialogComponent,
        TipoDeOperacionDeIngresoDeletePopupComponent
    ],
    entryComponents: [
        TipoDeOperacionDeIngresoComponent,
        TipoDeOperacionDeIngresoUpdateComponent,
        TipoDeOperacionDeIngresoDeleteDialogComponent,
        TipoDeOperacionDeIngresoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppTipoDeOperacionDeIngresoModule {}
