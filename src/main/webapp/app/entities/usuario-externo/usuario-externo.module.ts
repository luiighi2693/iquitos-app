import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import { IquitosAppAdminModule } from 'app/admin/admin.module';
import {
    UsuarioExternoComponent,
    UsuarioExternoDetailComponent,
    UsuarioExternoUpdateComponent,
    UsuarioExternoDeletePopupComponent,
    UsuarioExternoDeleteDialogComponent,
    usuarioExternoRoute,
    usuarioExternoPopupRoute
} from './';

const ENTITY_STATES = [...usuarioExternoRoute, ...usuarioExternoPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, IquitosAppAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UsuarioExternoComponent,
        UsuarioExternoDetailComponent,
        UsuarioExternoUpdateComponent,
        UsuarioExternoDeleteDialogComponent,
        UsuarioExternoDeletePopupComponent
    ],
    entryComponents: [
        UsuarioExternoComponent,
        UsuarioExternoUpdateComponent,
        UsuarioExternoDeleteDialogComponent,
        UsuarioExternoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppUsuarioExternoModule {}
