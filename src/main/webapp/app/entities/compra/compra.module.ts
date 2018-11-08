import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    CompraComponent,
    CompraDetailComponent,
    CompraUpdateComponent,
    CompraDeletePopupComponent,
    CompraDeleteDialogComponent,
    compraRoute,
    compraPopupRoute
} from './';

const ENTITY_STATES = [...compraRoute, ...compraPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CompraComponent, CompraDetailComponent, CompraUpdateComponent, CompraDeleteDialogComponent, CompraDeletePopupComponent],
    entryComponents: [CompraComponent, CompraUpdateComponent, CompraDeleteDialogComponent, CompraDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppCompraModule {}
