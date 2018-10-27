import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    OrderProductComponent,
    OrderProductDetailComponent,
    OrderProductUpdateComponent,
    OrderProductDeletePopupComponent,
    OrderProductDeleteDialogComponent,
    orderProductRoute,
    orderProductPopupRoute
} from './';

const ENTITY_STATES = [...orderProductRoute, ...orderProductPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrderProductComponent,
        OrderProductDetailComponent,
        OrderProductUpdateComponent,
        OrderProductDeleteDialogComponent,
        OrderProductDeletePopupComponent
    ],
    entryComponents: [
        OrderProductComponent,
        OrderProductUpdateComponent,
        OrderProductDeleteDialogComponent,
        OrderProductDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppOrderProductModule {}
