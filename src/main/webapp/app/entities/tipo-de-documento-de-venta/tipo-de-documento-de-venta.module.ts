import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    TipoDeDocumentoDeVentaComponent,
    TipoDeDocumentoDeVentaDetailComponent,
    TipoDeDocumentoDeVentaUpdateComponent,
    TipoDeDocumentoDeVentaDeletePopupComponent,
    TipoDeDocumentoDeVentaDeleteDialogComponent,
    tipoDeDocumentoDeVentaRoute,
    tipoDeDocumentoDeVentaPopupRoute
} from './';

const ENTITY_STATES = [...tipoDeDocumentoDeVentaRoute, ...tipoDeDocumentoDeVentaPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoDeDocumentoDeVentaComponent,
        TipoDeDocumentoDeVentaDetailComponent,
        TipoDeDocumentoDeVentaUpdateComponent,
        TipoDeDocumentoDeVentaDeleteDialogComponent,
        TipoDeDocumentoDeVentaDeletePopupComponent
    ],
    entryComponents: [
        TipoDeDocumentoDeVentaComponent,
        TipoDeDocumentoDeVentaUpdateComponent,
        TipoDeDocumentoDeVentaDeleteDialogComponent,
        TipoDeDocumentoDeVentaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppTipoDeDocumentoDeVentaModule {}
