import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDocumentType } from 'app/shared/model/document-type.model';
import { DocumentTypeService } from './document-type.service';

@Component({
    selector: 'jhi-document-type-update',
    templateUrl: './document-type-update.component.html'
})
export class DocumentTypeUpdateComponent implements OnInit {
    documentType: IDocumentType;
    isSaving: boolean;

    constructor(private documentTypeService: DocumentTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ documentType }) => {
            this.documentType = documentType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.documentType.id !== undefined) {
            this.subscribeToSaveResponse(this.documentTypeService.update(this.documentType));
        } else {
            this.subscribeToSaveResponse(this.documentTypeService.create(this.documentType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentType>>) {
        result.subscribe((res: HttpResponse<IDocumentType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
