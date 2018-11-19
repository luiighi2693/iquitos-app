import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ProductosRelacionadosTagsComponent,
    ProductosRelacionadosTagsDetailComponent,
    ProductosRelacionadosTagsUpdateComponent,
    ProductosRelacionadosTagsDeletePopupComponent,
    ProductosRelacionadosTagsDeleteDialogComponent,
    productosRelacionadosTagsRoute,
    productosRelacionadosTagsPopupRoute
} from './';

const ENTITY_STATES = [...productosRelacionadosTagsRoute, ...productosRelacionadosTagsPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductosRelacionadosTagsComponent,
        ProductosRelacionadosTagsDetailComponent,
        ProductosRelacionadosTagsUpdateComponent,
        ProductosRelacionadosTagsDeleteDialogComponent,
        ProductosRelacionadosTagsDeletePopupComponent
    ],
    entryComponents: [
        ProductosRelacionadosTagsComponent,
        ProductosRelacionadosTagsUpdateComponent,
        ProductosRelacionadosTagsDeleteDialogComponent,
        ProductosRelacionadosTagsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppProductosRelacionadosTagsModule {}
