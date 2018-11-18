import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    PagoDeProveedorComponent,
    PagoDeProveedorDetailComponent,
    PagoDeProveedorUpdateComponent,
    PagoDeProveedorDeletePopupComponent,
    PagoDeProveedorDeleteDialogComponent,
    pagoDeProveedorRoute,
    pagoDeProveedorPopupRoute
} from './';

const ENTITY_STATES = [...pagoDeProveedorRoute, ...pagoDeProveedorPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PagoDeProveedorComponent,
        PagoDeProveedorDetailComponent,
        PagoDeProveedorUpdateComponent,
        PagoDeProveedorDeleteDialogComponent,
        PagoDeProveedorDeletePopupComponent
    ],
    entryComponents: [
        PagoDeProveedorComponent,
        PagoDeProveedorUpdateComponent,
        PagoDeProveedorDeleteDialogComponent,
        PagoDeProveedorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppPagoDeProveedorModule {}
