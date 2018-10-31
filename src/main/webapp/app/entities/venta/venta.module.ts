import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    VentaComponent,
    VentaDetailComponent,
    VentaUpdateComponent,
    VentaDeletePopupComponent,
    VentaDeleteDialogComponent,
    ventaRoute,
    ventaPopupRoute
} from './';

const ENTITY_STATES = [...ventaRoute, ...ventaPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [VentaComponent, VentaDetailComponent, VentaUpdateComponent, VentaDeleteDialogComponent, VentaDeletePopupComponent],
    entryComponents: [VentaComponent, VentaUpdateComponent, VentaDeleteDialogComponent, VentaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppVentaModule {}
