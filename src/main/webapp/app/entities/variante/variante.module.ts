import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    VarianteComponent,
    VarianteDetailComponent,
    VarianteUpdateComponent,
    VarianteDeletePopupComponent,
    VarianteDeleteDialogComponent,
    varianteRoute,
    variantePopupRoute
} from './';

const ENTITY_STATES = [...varianteRoute, ...variantePopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VarianteComponent,
        VarianteDetailComponent,
        VarianteUpdateComponent,
        VarianteDeleteDialogComponent,
        VarianteDeletePopupComponent
    ],
    entryComponents: [VarianteComponent, VarianteUpdateComponent, VarianteDeleteDialogComponent, VarianteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppVarianteModule {}
