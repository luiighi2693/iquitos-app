import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    SellHasProductComponent,
    SellHasProductDetailComponent,
    SellHasProductUpdateComponent,
    SellHasProductDeletePopupComponent,
    SellHasProductDeleteDialogComponent,
    sellHasProductRoute,
    sellHasProductPopupRoute
} from './';

const ENTITY_STATES = [...sellHasProductRoute, ...sellHasProductPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SellHasProductComponent,
        SellHasProductDetailComponent,
        SellHasProductUpdateComponent,
        SellHasProductDeleteDialogComponent,
        SellHasProductDeletePopupComponent
    ],
    entryComponents: [
        SellHasProductComponent,
        SellHasProductUpdateComponent,
        SellHasProductDeleteDialogComponent,
        SellHasProductDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppSellHasProductModule {}
