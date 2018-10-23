import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptOperationOutput } from 'app/shared/model/concept-operation-output.model';

@Component({
    selector: 'jhi-concept-operation-output-detail',
    templateUrl: './concept-operation-output-detail.component.html'
})
export class ConceptOperationOutputDetailComponent implements OnInit {
    conceptOperationOutput: IConceptOperationOutput;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ conceptOperationOutput }) => {
            this.conceptOperationOutput = conceptOperationOutput;
        });
    }

    previousState() {
        window.history.back();
    }
}
