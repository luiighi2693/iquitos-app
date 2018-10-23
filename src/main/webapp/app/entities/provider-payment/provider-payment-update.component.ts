import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IProviderPayment } from 'app/shared/model/provider-payment.model';
import { ProviderPaymentService } from './provider-payment.service';
import { IDocumentTypePurchase } from 'app/shared/model/document-type-purchase.model';
import { DocumentTypePurchaseService } from 'app/entities/document-type-purchase';
import { IPaymentType } from 'app/shared/model/payment-type.model';
import { PaymentTypeService } from 'app/entities/payment-type';

@Component({
    selector: 'jhi-provider-payment-update',
    templateUrl: './provider-payment-update.component.html'
})
export class ProviderPaymentUpdateComponent implements OnInit {
    providerPayment: IProviderPayment;
    isSaving: boolean;

    documenttypepurchases: IDocumentTypePurchase[];

    paymenttypes: IPaymentType[];
    emissionDateDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private providerPaymentService: ProviderPaymentService,
        private documentTypePurchaseService: DocumentTypePurchaseService,
        private paymentTypeService: PaymentTypeService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ providerPayment }) => {
            this.providerPayment = providerPayment;
        });
        this.documentTypePurchaseService.query().subscribe(
            (res: HttpResponse<IDocumentTypePurchase[]>) => {
                this.documenttypepurchases = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.paymentTypeService.query().subscribe(
            (res: HttpResponse<IPaymentType[]>) => {
                this.paymenttypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.providerPayment, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.providerPayment.id !== undefined) {
            this.subscribeToSaveResponse(this.providerPaymentService.update(this.providerPayment));
        } else {
            this.subscribeToSaveResponse(this.providerPaymentService.create(this.providerPayment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProviderPayment>>) {
        result.subscribe((res: HttpResponse<IProviderPayment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDocumentTypePurchaseById(index: number, item: IDocumentTypePurchase) {
        return item.id;
    }

    trackPaymentTypeById(index: number, item: IPaymentType) {
        return item.id;
    }
}
