import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ParametroSistemaComponent,
    ParametroSistemaDetailComponent,
    ParametroSistemaUpdateComponent,
    ParametroSistemaDeletePopupComponent,
    ParametroSistemaDeleteDialogComponent,
    parametroSistemaRoute,
    parametroSistemaPopupRoute
} from './';

const ENTITY_STATES = [...parametroSistemaRoute, ...parametroSistemaPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ParametroSistemaComponent,
        ParametroSistemaDetailComponent,
        ParametroSistemaUpdateComponent,
        ParametroSistemaDeleteDialogComponent,
        ParametroSistemaDeletePopupComponent
    ],
    entryComponents: [
        ParametroSistemaComponent,
        ParametroSistemaUpdateComponent,
        ParametroSistemaDeleteDialogComponent,
        ParametroSistemaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppParametroSistemaModule {}
