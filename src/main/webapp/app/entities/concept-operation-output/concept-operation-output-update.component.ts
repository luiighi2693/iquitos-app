import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IConceptOperationOutput } from 'app/shared/model/concept-operation-output.model';
import { ConceptOperationOutputService } from './concept-operation-output.service';

@Component({
    selector: 'jhi-concept-operation-output-update',
    templateUrl: './concept-operation-output-update.component.html'
})
export class ConceptOperationOutputUpdateComponent implements OnInit {
    conceptOperationOutput: IConceptOperationOutput;
    isSaving: boolean;

    constructor(private conceptOperationOutputService: ConceptOperationOutputService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ conceptOperationOutput }) => {
            this.conceptOperationOutput = conceptOperationOutput;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.conceptOperationOutput.id !== undefined) {
            this.subscribeToSaveResponse(this.conceptOperationOutputService.update(this.conceptOperationOutput));
        } else {
            this.subscribeToSaveResponse(this.conceptOperationOutputService.create(this.conceptOperationOutput));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConceptOperationOutput>>) {
        result.subscribe(
            (res: HttpResponse<IConceptOperationOutput>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
