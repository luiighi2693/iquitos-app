import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    CajaComponent,
    CajaDetailComponent,
    CajaUpdateComponent,
    CajaDeletePopupComponent,
    CajaDeleteDialogComponent,
    cajaRoute,
    cajaPopupRoute
} from './';

const ENTITY_STATES = [...cajaRoute, ...cajaPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CajaComponent, CajaDetailComponent, CajaUpdateComponent, CajaDeleteDialogComponent, CajaDeletePopupComponent],
    entryComponents: [CajaComponent, CajaUpdateComponent, CajaDeleteDialogComponent, CajaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppCajaModule {}
