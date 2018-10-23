import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ProviderAccountComponent,
    ProviderAccountDetailComponent,
    ProviderAccountUpdateComponent,
    ProviderAccountDeletePopupComponent,
    ProviderAccountDeleteDialogComponent,
    providerAccountRoute,
    providerAccountPopupRoute
} from './';

const ENTITY_STATES = [...providerAccountRoute, ...providerAccountPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProviderAccountComponent,
        ProviderAccountDetailComponent,
        ProviderAccountUpdateComponent,
        ProviderAccountDeleteDialogComponent,
        ProviderAccountDeletePopupComponent
    ],
    entryComponents: [
        ProviderAccountComponent,
        ProviderAccountUpdateComponent,
        ProviderAccountDeleteDialogComponent,
        ProviderAccountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppProviderAccountModule {}
