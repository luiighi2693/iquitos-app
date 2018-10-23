import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ConceptOperationOutputComponent,
    ConceptOperationOutputDetailComponent,
    ConceptOperationOutputUpdateComponent,
    ConceptOperationOutputDeletePopupComponent,
    ConceptOperationOutputDeleteDialogComponent,
    conceptOperationOutputRoute,
    conceptOperationOutputPopupRoute
} from './';

const ENTITY_STATES = [...conceptOperationOutputRoute, ...conceptOperationOutputPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConceptOperationOutputComponent,
        ConceptOperationOutputDetailComponent,
        ConceptOperationOutputUpdateComponent,
        ConceptOperationOutputDeleteDialogComponent,
        ConceptOperationOutputDeletePopupComponent
    ],
    entryComponents: [
        ConceptOperationOutputComponent,
        ConceptOperationOutputUpdateComponent,
        ConceptOperationOutputDeleteDialogComponent,
        ConceptOperationOutputDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppConceptOperationOutputModule {}
