import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    TipoDeDocumentoComponent,
    TipoDeDocumentoDetailComponent,
    TipoDeDocumentoUpdateComponent,
    TipoDeDocumentoDeletePopupComponent,
    TipoDeDocumentoDeleteDialogComponent,
    tipoDeDocumentoRoute,
    tipoDeDocumentoPopupRoute
} from './';

const ENTITY_STATES = [...tipoDeDocumentoRoute, ...tipoDeDocumentoPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoDeDocumentoComponent,
        TipoDeDocumentoDetailComponent,
        TipoDeDocumentoUpdateComponent,
        TipoDeDocumentoDeleteDialogComponent,
        TipoDeDocumentoDeletePopupComponent
    ],
    entryComponents: [
        TipoDeDocumentoComponent,
        TipoDeDocumentoUpdateComponent,
        TipoDeDocumentoDeleteDialogComponent,
        TipoDeDocumentoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppTipoDeDocumentoModule {}
