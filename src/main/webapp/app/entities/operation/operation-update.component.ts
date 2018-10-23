import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IOperation } from 'app/shared/model/operation.model';
import { OperationService } from './operation.service';
import { IBox } from 'app/shared/model/box.model';
import { BoxService } from 'app/entities/box';
import { IPaymentType } from 'app/shared/model/payment-type.model';
import { PaymentTypeService } from 'app/entities/payment-type';
import { IConceptOperationInput } from 'app/shared/model/concept-operation-input.model';
import { ConceptOperationInputService } from 'app/entities/concept-operation-input';
import { IConceptOperationOutput } from 'app/shared/model/concept-operation-output.model';
import { ConceptOperationOutputService } from 'app/entities/concept-operation-output';

@Component({
    selector: 'jhi-operation-update',
    templateUrl: './operation-update.component.html'
})
export class OperationUpdateComponent implements OnInit {
    operation: IOperation;
    isSaving: boolean;

    boxes: IBox[];

    paymenttypes: IPaymentType[];

    conceptoperationinputs: IConceptOperationInput[];

    conceptoperationoutputs: IConceptOperationOutput[];
    initDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private operationService: OperationService,
        private boxService: BoxService,
        private paymentTypeService: PaymentTypeService,
        private conceptOperationInputService: ConceptOperationInputService,
        private conceptOperationOutputService: ConceptOperationOutputService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ operation }) => {
            this.operation = operation;
        });
        this.boxService.query().subscribe(
            (res: HttpResponse<IBox[]>) => {
                this.boxes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.paymentTypeService.query().subscribe(
            (res: HttpResponse<IPaymentType[]>) => {
                this.paymenttypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.conceptOperationInputService.query().subscribe(
            (res: HttpResponse<IConceptOperationInput[]>) => {
                this.conceptoperationinputs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.conceptOperationOutputService.query().subscribe(
            (res: HttpResponse<IConceptOperationOutput[]>) => {
                this.conceptoperationoutputs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.operation.id !== undefined) {
            this.subscribeToSaveResponse(this.operationService.update(this.operation));
        } else {
            this.subscribeToSaveResponse(this.operationService.create(this.operation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOperation>>) {
        result.subscribe((res: HttpResponse<IOperation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBoxById(index: number, item: IBox) {
        return item.id;
    }

    trackPaymentTypeById(index: number, item: IPaymentType) {
        return item.id;
    }

    trackConceptOperationInputById(index: number, item: IConceptOperationInput) {
        return item.id;
    }

    trackConceptOperationOutputById(index: number, item: IConceptOperationOutput) {
        return item.id;
    }
}
