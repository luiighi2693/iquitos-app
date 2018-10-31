import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    UnidadDeMedidaComponent,
    UnidadDeMedidaDetailComponent,
    UnidadDeMedidaUpdateComponent,
    UnidadDeMedidaDeletePopupComponent,
    UnidadDeMedidaDeleteDialogComponent,
    unidadDeMedidaRoute,
    unidadDeMedidaPopupRoute
} from './';

const ENTITY_STATES = [...unidadDeMedidaRoute, ...unidadDeMedidaPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UnidadDeMedidaComponent,
        UnidadDeMedidaDetailComponent,
        UnidadDeMedidaUpdateComponent,
        UnidadDeMedidaDeleteDialogComponent,
        UnidadDeMedidaDeletePopupComponent
    ],
    entryComponents: [
        UnidadDeMedidaComponent,
        UnidadDeMedidaUpdateComponent,
        UnidadDeMedidaDeleteDialogComponent,
        UnidadDeMedidaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppUnidadDeMedidaModule {}
