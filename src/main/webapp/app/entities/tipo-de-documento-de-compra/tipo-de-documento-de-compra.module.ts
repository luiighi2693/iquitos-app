import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    TipoDeDocumentoDeCompraComponent,
    TipoDeDocumentoDeCompraDetailComponent,
    TipoDeDocumentoDeCompraUpdateComponent,
    TipoDeDocumentoDeCompraDeletePopupComponent,
    TipoDeDocumentoDeCompraDeleteDialogComponent,
    tipoDeDocumentoDeCompraRoute,
    tipoDeDocumentoDeCompraPopupRoute
} from './';

const ENTITY_STATES = [...tipoDeDocumentoDeCompraRoute, ...tipoDeDocumentoDeCompraPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoDeDocumentoDeCompraComponent,
        TipoDeDocumentoDeCompraDetailComponent,
        TipoDeDocumentoDeCompraUpdateComponent,
        TipoDeDocumentoDeCompraDeleteDialogComponent,
        TipoDeDocumentoDeCompraDeletePopupComponent
    ],
    entryComponents: [
        TipoDeDocumentoDeCompraComponent,
        TipoDeDocumentoDeCompraUpdateComponent,
        TipoDeDocumentoDeCompraDeleteDialogComponent,
        TipoDeDocumentoDeCompraDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppTipoDeDocumentoDeCompraModule {}
