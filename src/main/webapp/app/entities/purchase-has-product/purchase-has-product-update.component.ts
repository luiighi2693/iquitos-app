import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IPurchaseHasProduct } from 'app/shared/model/purchase-has-product.model';
import { PurchaseHasProductService } from './purchase-has-product.service';
import { IPurchase } from 'app/shared/model/purchase.model';
import { PurchaseService } from 'app/entities/purchase';

@Component({
    selector: 'jhi-purchase-has-product-update',
    templateUrl: './purchase-has-product-update.component.html'
})
export class PurchaseHasProductUpdateComponent implements OnInit {
    purchaseHasProduct: IPurchaseHasProduct;
    isSaving: boolean;

    purchases: IPurchase[];
    datePurchaseDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private purchaseHasProductService: PurchaseHasProductService,
        private purchaseService: PurchaseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseHasProduct }) => {
            this.purchaseHasProduct = purchaseHasProduct;
        });
        this.purchaseService.query({ filter: 'purchasehasproduct-is-null' }).subscribe(
            (res: HttpResponse<IPurchase[]>) => {
                if (!this.purchaseHasProduct.purchaseId) {
                    this.purchases = res.body;
                } else {
                    this.purchaseService.find(this.purchaseHasProduct.purchaseId).subscribe(
                        (subRes: HttpResponse<IPurchase>) => {
                            this.purchases = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.purchaseHasProduct.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseHasProductService.update(this.purchaseHasProduct));
        } else {
            this.subscribeToSaveResponse(this.purchaseHasProductService.create(this.purchaseHasProduct));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseHasProduct>>) {
        result.subscribe((res: HttpResponse<IPurchaseHasProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPurchaseById(index: number, item: IPurchase) {
        return item.id;
    }
}
