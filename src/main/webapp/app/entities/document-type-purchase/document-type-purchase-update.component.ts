import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDocumentTypePurchase } from 'app/shared/model/document-type-purchase.model';
import { DocumentTypePurchaseService } from './document-type-purchase.service';

@Component({
    selector: 'jhi-document-type-purchase-update',
    templateUrl: './document-type-purchase-update.component.html'
})
export class DocumentTypePurchaseUpdateComponent implements OnInit {
    documentTypePurchase: IDocumentTypePurchase;
    isSaving: boolean;

    constructor(private documentTypePurchaseService: DocumentTypePurchaseService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ documentTypePurchase }) => {
            this.documentTypePurchase = documentTypePurchase;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.documentTypePurchase.id !== undefined) {
            this.subscribeToSaveResponse(this.documentTypePurchaseService.update(this.documentTypePurchase));
        } else {
            this.subscribeToSaveResponse(this.documentTypePurchaseService.create(this.documentTypePurchase));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentTypePurchase>>) {
        result.subscribe(
            (res: HttpResponse<IDocumentTypePurchase>) => this.onSaveSuccess(),
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
