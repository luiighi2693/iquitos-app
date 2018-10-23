import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPaymentType } from 'app/shared/model/payment-type.model';
import { PaymentTypeService } from './payment-type.service';

@Component({
    selector: 'jhi-payment-type-update',
    templateUrl: './payment-type-update.component.html'
})
export class PaymentTypeUpdateComponent implements OnInit {
    paymentType: IPaymentType;
    isSaving: boolean;

    constructor(private paymentTypeService: PaymentTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paymentType }) => {
            this.paymentType = paymentType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.paymentType.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentTypeService.update(this.paymentType));
        } else {
            this.subscribeToSaveResponse(this.paymentTypeService.create(this.paymentType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentType>>) {
        result.subscribe((res: HttpResponse<IPaymentType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
