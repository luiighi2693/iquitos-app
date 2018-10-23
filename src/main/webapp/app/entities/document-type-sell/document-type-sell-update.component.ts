import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDocumentTypeSell } from 'app/shared/model/document-type-sell.model';
import { DocumentTypeSellService } from './document-type-sell.service';

@Component({
    selector: 'jhi-document-type-sell-update',
    templateUrl: './document-type-sell-update.component.html'
})
export class DocumentTypeSellUpdateComponent implements OnInit {
    documentTypeSell: IDocumentTypeSell;
    isSaving: boolean;

    constructor(private documentTypeSellService: DocumentTypeSellService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ documentTypeSell }) => {
            this.documentTypeSell = documentTypeSell;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.documentTypeSell.id !== undefined) {
            this.subscribeToSaveResponse(this.documentTypeSellService.update(this.documentTypeSell));
        } else {
            this.subscribeToSaveResponse(this.documentTypeSellService.create(this.documentTypeSell));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentTypeSell>>) {
        result.subscribe((res: HttpResponse<IDocumentTypeSell>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
