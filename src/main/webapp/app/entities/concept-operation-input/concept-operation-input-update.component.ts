import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IConceptOperationInput } from 'app/shared/model/concept-operation-input.model';
import { ConceptOperationInputService } from './concept-operation-input.service';

@Component({
    selector: 'jhi-concept-operation-input-update',
    templateUrl: './concept-operation-input-update.component.html'
})
export class ConceptOperationInputUpdateComponent implements OnInit {
    conceptOperationInput: IConceptOperationInput;
    isSaving: boolean;

    constructor(private conceptOperationInputService: ConceptOperationInputService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ conceptOperationInput }) => {
            this.conceptOperationInput = conceptOperationInput;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.conceptOperationInput.id !== undefined) {
            this.subscribeToSaveResponse(this.conceptOperationInputService.update(this.conceptOperationInput));
        } else {
            this.subscribeToSaveResponse(this.conceptOperationInputService.create(this.conceptOperationInput));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConceptOperationInput>>) {
        result.subscribe(
            (res: HttpResponse<IConceptOperationInput>) => this.onSaveSuccess(),
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
