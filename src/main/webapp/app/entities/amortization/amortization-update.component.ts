import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IAmortization } from 'app/shared/model/amortization.model';
import { AmortizationService } from './amortization.service';
import { IDocumentTypeSell } from 'app/shared/model/document-type-sell.model';
import { DocumentTypeSellService } from 'app/entities/document-type-sell';
import { IPaymentType } from 'app/shared/model/payment-type.model';
import { PaymentTypeService } from 'app/entities/payment-type';
import { ISell } from 'app/shared/model/sell.model';
import { SellService } from 'app/entities/sell';

@Component({
    selector: 'jhi-amortization-update',
    templateUrl: './amortization-update.component.html'
})
export class AmortizationUpdateComponent implements OnInit {
    amortization: IAmortization;
    isSaving: boolean;

    documenttypesells: IDocumentTypeSell[];

    paymenttypes: IPaymentType[];

    sells: ISell[];
    emissionDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private amortizationService: AmortizationService,
        private documentTypeSellService: DocumentTypeSellService,
        private paymentTypeService: PaymentTypeService,
        private sellService: SellService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ amortization }) => {
            this.amortization = amortization;
        });
        this.documentTypeSellService.query().subscribe(
            (res: HttpResponse<IDocumentTypeSell[]>) => {
                this.documenttypesells = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.paymentTypeService.query().subscribe(
            (res: HttpResponse<IPaymentType[]>) => {
                this.paymenttypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.sellService.query().subscribe(
            (res: HttpResponse<ISell[]>) => {
                this.sells = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.amortization.id !== undefined) {
            this.subscribeToSaveResponse(this.amortizationService.update(this.amortization));
        } else {
            this.subscribeToSaveResponse(this.amortizationService.create(this.amortization));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAmortization>>) {
        result.subscribe((res: HttpResponse<IAmortization>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDocumentTypeSellById(index: number, item: IDocumentTypeSell) {
        return item.id;
    }

    trackPaymentTypeById(index: number, item: IPaymentType) {
        return item.id;
    }

    trackSellById(index: number, item: ISell) {
        return item.id;
    }
}
