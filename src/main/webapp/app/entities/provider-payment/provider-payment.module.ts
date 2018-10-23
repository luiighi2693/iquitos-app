import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ProviderPaymentComponent,
    ProviderPaymentDetailComponent,
    ProviderPaymentUpdateComponent,
    ProviderPaymentDeletePopupComponent,
    ProviderPaymentDeleteDialogComponent,
    providerPaymentRoute,
    providerPaymentPopupRoute
} from './';

const ENTITY_STATES = [...providerPaymentRoute, ...providerPaymentPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProviderPaymentComponent,
        ProviderPaymentDetailComponent,
        ProviderPaymentUpdateComponent,
        ProviderPaymentDeleteDialogComponent,
        ProviderPaymentDeletePopupComponent
    ],
    entryComponents: [
        ProviderPaymentComponent,
        ProviderPaymentUpdateComponent,
        ProviderPaymentDeleteDialogComponent,
        ProviderPaymentDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppProviderPaymentModule {}
