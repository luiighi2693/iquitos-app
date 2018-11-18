import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ContactoProveedorComponent,
    ContactoProveedorDetailComponent,
    ContactoProveedorUpdateComponent,
    ContactoProveedorDeletePopupComponent,
    ContactoProveedorDeleteDialogComponent,
    contactoProveedorRoute,
    contactoProveedorPopupRoute
} from './';

const ENTITY_STATES = [...contactoProveedorRoute, ...contactoProveedorPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContactoProveedorComponent,
        ContactoProveedorDetailComponent,
        ContactoProveedorUpdateComponent,
        ContactoProveedorDeleteDialogComponent,
        ContactoProveedorDeletePopupComponent
    ],
    entryComponents: [
        ContactoProveedorComponent,
        ContactoProveedorUpdateComponent,
        ContactoProveedorDeleteDialogComponent,
        ContactoProveedorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppContactoProveedorModule {}
