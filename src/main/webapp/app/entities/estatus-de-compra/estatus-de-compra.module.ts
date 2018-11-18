import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    EstatusDeCompraComponent,
    EstatusDeCompraDetailComponent,
    EstatusDeCompraUpdateComponent,
    EstatusDeCompraDeletePopupComponent,
    EstatusDeCompraDeleteDialogComponent,
    estatusDeCompraRoute,
    estatusDeCompraPopupRoute
} from './';

const ENTITY_STATES = [...estatusDeCompraRoute, ...estatusDeCompraPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EstatusDeCompraComponent,
        EstatusDeCompraDetailComponent,
        EstatusDeCompraUpdateComponent,
        EstatusDeCompraDeleteDialogComponent,
        EstatusDeCompraDeletePopupComponent
    ],
    entryComponents: [
        EstatusDeCompraComponent,
        EstatusDeCompraUpdateComponent,
        EstatusDeCompraDeleteDialogComponent,
        EstatusDeCompraDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppEstatusDeCompraModule {}
