import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPurchaseStatus } from 'app/shared/model/purchase-status.model';
import { PurchaseStatusService } from './purchase-status.service';

@Component({
    selector: 'jhi-purchase-status-update',
    templateUrl: './purchase-status-update.component.html'
})
export class PurchaseStatusUpdateComponent implements OnInit {
    purchaseStatus: IPurchaseStatus;
    isSaving: boolean;

    constructor(private purchaseStatusService: PurchaseStatusService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseStatus }) => {
            this.purchaseStatus = purchaseStatus;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.purchaseStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseStatusService.update(this.purchaseStatus));
        } else {
            this.subscribeToSaveResponse(this.purchaseStatusService.create(this.purchaseStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseStatus>>) {
        result.subscribe((res: HttpResponse<IPurchaseStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
