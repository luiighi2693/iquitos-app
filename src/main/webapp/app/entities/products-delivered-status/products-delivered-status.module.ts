import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ProductsDeliveredStatusComponent,
    ProductsDeliveredStatusDetailComponent,
    ProductsDeliveredStatusUpdateComponent,
    ProductsDeliveredStatusDeletePopupComponent,
    ProductsDeliveredStatusDeleteDialogComponent,
    productsDeliveredStatusRoute,
    productsDeliveredStatusPopupRoute
} from './';

const ENTITY_STATES = [...productsDeliveredStatusRoute, ...productsDeliveredStatusPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductsDeliveredStatusComponent,
        ProductsDeliveredStatusDetailComponent,
        ProductsDeliveredStatusUpdateComponent,
        ProductsDeliveredStatusDeleteDialogComponent,
        ProductsDeliveredStatusDeletePopupComponent
    ],
    entryComponents: [
        ProductsDeliveredStatusComponent,
        ProductsDeliveredStatusUpdateComponent,
        ProductsDeliveredStatusDeleteDialogComponent,
        ProductsDeliveredStatusDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppProductsDeliveredStatusModule {}
