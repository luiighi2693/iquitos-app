import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    CuentaProveedorComponent,
    CuentaProveedorDetailComponent,
    CuentaProveedorUpdateComponent,
    CuentaProveedorDeletePopupComponent,
    CuentaProveedorDeleteDialogComponent,
    cuentaProveedorRoute,
    cuentaProveedorPopupRoute
} from './';

const ENTITY_STATES = [...cuentaProveedorRoute, ...cuentaProveedorPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CuentaProveedorComponent,
        CuentaProveedorDetailComponent,
        CuentaProveedorUpdateComponent,
        CuentaProveedorDeleteDialogComponent,
        CuentaProveedorDeletePopupComponent
    ],
    entryComponents: [
        CuentaProveedorComponent,
        CuentaProveedorUpdateComponent,
        CuentaProveedorDeleteDialogComponent,
        CuentaProveedorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppCuentaProveedorModule {}
