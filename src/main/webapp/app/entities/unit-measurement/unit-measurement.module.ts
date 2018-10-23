import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    UnitMeasurementComponent,
    UnitMeasurementDetailComponent,
    UnitMeasurementUpdateComponent,
    UnitMeasurementDeletePopupComponent,
    UnitMeasurementDeleteDialogComponent,
    unitMeasurementRoute,
    unitMeasurementPopupRoute
} from './';

const ENTITY_STATES = [...unitMeasurementRoute, ...unitMeasurementPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UnitMeasurementComponent,
        UnitMeasurementDetailComponent,
        UnitMeasurementUpdateComponent,
        UnitMeasurementDeleteDialogComponent,
        UnitMeasurementDeletePopupComponent
    ],
    entryComponents: [
        UnitMeasurementComponent,
        UnitMeasurementUpdateComponent,
        UnitMeasurementDeleteDialogComponent,
        UnitMeasurementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppUnitMeasurementModule {}
