import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    UserLoginComponent,
    UserLoginDetailComponent,
    UserLoginUpdateComponent,
    UserLoginDeletePopupComponent,
    UserLoginDeleteDialogComponent,
    userLoginRoute,
    userLoginPopupRoute
} from './';

const ENTITY_STATES = [...userLoginRoute, ...userLoginPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserLoginComponent,
        UserLoginDetailComponent,
        UserLoginUpdateComponent,
        UserLoginDeleteDialogComponent,
        UserLoginDeletePopupComponent
    ],
    entryComponents: [UserLoginComponent, UserLoginUpdateComponent, UserLoginDeleteDialogComponent, UserLoginDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppUserLoginModule {}
