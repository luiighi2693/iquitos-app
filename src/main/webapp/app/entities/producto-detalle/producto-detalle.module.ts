import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ProductoDetalleComponent,
    ProductoDetalleDetailComponent,
    ProductoDetalleUpdateComponent,
    ProductoDetalleDeletePopupComponent,
    ProductoDetalleDeleteDialogComponent,
    productoDetalleRoute,
    productoDetallePopupRoute
} from './';

const ENTITY_STATES = [...productoDetalleRoute, ...productoDetallePopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductoDetalleComponent,
        ProductoDetalleDetailComponent,
        ProductoDetalleUpdateComponent,
        ProductoDetalleDeleteDialogComponent,
        ProductoDetalleDeletePopupComponent
    ],
    entryComponents: [
        ProductoDetalleComponent,
        ProductoDetalleUpdateComponent,
        ProductoDetalleDeleteDialogComponent,
        ProductoDetalleDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppProductoDetalleModule {}
