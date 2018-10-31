import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    TipoDeOperacionDeGastoComponent,
    TipoDeOperacionDeGastoDetailComponent,
    TipoDeOperacionDeGastoUpdateComponent,
    TipoDeOperacionDeGastoDeletePopupComponent,
    TipoDeOperacionDeGastoDeleteDialogComponent,
    tipoDeOperacionDeGastoRoute,
    tipoDeOperacionDeGastoPopupRoute
} from './';

const ENTITY_STATES = [...tipoDeOperacionDeGastoRoute, ...tipoDeOperacionDeGastoPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoDeOperacionDeGastoComponent,
        TipoDeOperacionDeGastoDetailComponent,
        TipoDeOperacionDeGastoUpdateComponent,
        TipoDeOperacionDeGastoDeleteDialogComponent,
        TipoDeOperacionDeGastoDeletePopupComponent
    ],
    entryComponents: [
        TipoDeOperacionDeGastoComponent,
        TipoDeOperacionDeGastoUpdateComponent,
        TipoDeOperacionDeGastoDeleteDialogComponent,
        TipoDeOperacionDeGastoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppTipoDeOperacionDeGastoModule {}
