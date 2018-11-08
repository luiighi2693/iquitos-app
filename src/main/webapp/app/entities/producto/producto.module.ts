import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ProductoComponent,
    ProductoDetailComponent,
    ProductoUpdateComponent,
    ProductoDeletePopupComponent,
    ProductoDeleteDialogComponent,
    productoRoute,
    productoPopupRoute
} from './';

const ENTITY_STATES = [...productoRoute, ...productoPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductoComponent,
        ProductoDetailComponent,
        ProductoUpdateComponent,
        ProductoDeleteDialogComponent,
        ProductoDeletePopupComponent
    ],
    entryComponents: [ProductoComponent, ProductoUpdateComponent, ProductoDeleteDialogComponent, ProductoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppProductoModule {}
