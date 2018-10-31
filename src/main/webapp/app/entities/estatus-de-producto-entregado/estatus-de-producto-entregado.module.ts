import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    EstatusDeProductoEntregadoComponent,
    EstatusDeProductoEntregadoDetailComponent,
    EstatusDeProductoEntregadoUpdateComponent,
    EstatusDeProductoEntregadoDeletePopupComponent,
    EstatusDeProductoEntregadoDeleteDialogComponent,
    estatusDeProductoEntregadoRoute,
    estatusDeProductoEntregadoPopupRoute
} from './';

const ENTITY_STATES = [...estatusDeProductoEntregadoRoute, ...estatusDeProductoEntregadoPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EstatusDeProductoEntregadoComponent,
        EstatusDeProductoEntregadoDetailComponent,
        EstatusDeProductoEntregadoUpdateComponent,
        EstatusDeProductoEntregadoDeleteDialogComponent,
        EstatusDeProductoEntregadoDeletePopupComponent
    ],
    entryComponents: [
        EstatusDeProductoEntregadoComponent,
        EstatusDeProductoEntregadoUpdateComponent,
        EstatusDeProductoEntregadoDeleteDialogComponent,
        EstatusDeProductoEntregadoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppEstatusDeProductoEntregadoModule {}
