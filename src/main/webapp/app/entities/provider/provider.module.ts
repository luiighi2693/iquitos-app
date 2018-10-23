import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ProviderComponent,
    ProviderDetailComponent,
    ProviderUpdateComponent,
    ProviderDeletePopupComponent,
    ProviderDeleteDialogComponent,
    providerRoute,
    providerPopupRoute
} from './';

const ENTITY_STATES = [...providerRoute, ...providerPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProviderComponent,
        ProviderDetailComponent,
        ProviderUpdateComponent,
        ProviderDeleteDialogComponent,
        ProviderDeletePopupComponent
    ],
    entryComponents: [ProviderComponent, ProviderUpdateComponent, ProviderDeleteDialogComponent, ProviderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppProviderModule {}
