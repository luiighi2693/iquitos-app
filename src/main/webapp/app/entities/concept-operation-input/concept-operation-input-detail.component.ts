import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptOperationInput } from 'app/shared/model/concept-operation-input.model';

@Component({
    selector: 'jhi-concept-operation-input-detail',
    templateUrl: './concept-operation-input-detail.component.html'
})
export class ConceptOperationInputDetailComponent implements OnInit {
    conceptOperationInput: IConceptOperationInput;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ conceptOperationInput }) => {
            this.conceptOperationInput = conceptOperationInput;
        });
    }

    previousState() {
        window.history.back();
    }
}
