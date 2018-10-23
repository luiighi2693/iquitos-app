import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ICredit } from 'app/shared/model/credit.model';
import { CreditService } from './credit.service';
import { ISell } from 'app/shared/model/sell.model';
import { SellService } from 'app/entities/sell';
import { IPurchase } from 'app/shared/model/purchase.model';
import { PurchaseService } from 'app/entities/purchase';

@Component({
    selector: 'jhi-credit-update',
    templateUrl: './credit-update.component.html'
})
export class CreditUpdateComponent implements OnInit {
    credit: ICredit;
    isSaving: boolean;

    sells: ISell[];

    purchases: IPurchase[];
    emissionDateDp: any;
    limitDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private creditService: CreditService,
        private sellService: SellService,
        private purchaseService: PurchaseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ credit }) => {
            this.credit = credit;
        });
        this.sellService.query().subscribe(
            (res: HttpResponse<ISell[]>) => {
                this.sells = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.purchaseService.query().subscribe(
            (res: HttpResponse<IPurchase[]>) => {
                this.purchases = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.credit.id !== undefined) {
            this.subscribeToSaveResponse(this.creditService.update(this.credit));
        } else {
            this.subscribeToSaveResponse(this.creditService.create(this.credit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICredit>>) {
        result.subscribe((res: HttpResponse<ICredit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSellById(index: number, item: ISell) {
        return item.id;
    }

    trackPurchaseById(index: number, item: IPurchase) {
        return item.id;
    }
}
