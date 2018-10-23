import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    BoxComponent,
    BoxDetailComponent,
    BoxUpdateComponent,
    BoxDeletePopupComponent,
    BoxDeleteDialogComponent,
    boxRoute,
    boxPopupRoute
} from './';

const ENTITY_STATES = [...boxRoute, ...boxPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BoxComponent, BoxDetailComponent, BoxUpdateComponent, BoxDeleteDialogComponent, BoxDeletePopupComponent],
    entryComponents: [BoxComponent, BoxUpdateComponent, BoxDeleteDialogComponent, BoxDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppBoxModule {}
