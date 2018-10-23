import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    SellComponent,
    SellDetailComponent,
    SellUpdateComponent,
    SellDeletePopupComponent,
    SellDeleteDialogComponent,
    sellRoute,
    sellPopupRoute
} from './';

const ENTITY_STATES = [...sellRoute, ...sellPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SellComponent, SellDetailComponent, SellUpdateComponent, SellDeleteDialogComponent, SellDeletePopupComponent],
    entryComponents: [SellComponent, SellUpdateComponent, SellDeleteDialogComponent, SellDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppSellModule {}
