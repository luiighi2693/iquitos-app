import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IquitosAppSharedModule } from 'app/shared';
import {
    ConceptOperationInputComponent,
    ConceptOperationInputDetailComponent,
    ConceptOperationInputUpdateComponent,
    ConceptOperationInputDeletePopupComponent,
    ConceptOperationInputDeleteDialogComponent,
    conceptOperationInputRoute,
    conceptOperationInputPopupRoute
} from './';

const ENTITY_STATES = [...conceptOperationInputRoute, ...conceptOperationInputPopupRoute];

@NgModule({
    imports: [IquitosAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConceptOperationInputComponent,
        ConceptOperationInputDetailComponent,
        ConceptOperationInputUpdateComponent,
        ConceptOperationInputDeleteDialogComponent,
        ConceptOperationInputDeletePopupComponent
    ],
    entryComponents: [
        ConceptOperationInputComponent,
        ConceptOperationInputUpdateComponent,
        ConceptOperationInputDeleteDialogComponent,
        ConceptOperationInputDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IquitosAppConceptOperationInputModule {}
