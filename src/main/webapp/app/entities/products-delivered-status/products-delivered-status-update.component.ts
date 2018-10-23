import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IProductsDeliveredStatus } from 'app/shared/model/products-delivered-status.model';
import { ProductsDeliveredStatusService } from './products-delivered-status.service';

@Component({
    selector: 'jhi-products-delivered-status-update',
    templateUrl: './products-delivered-status-update.component.html'
})
export class ProductsDeliveredStatusUpdateComponent implements OnInit {
    productsDeliveredStatus: IProductsDeliveredStatus;
    isSaving: boolean;

    constructor(private productsDeliveredStatusService: ProductsDeliveredStatusService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productsDeliveredStatus }) => {
            this.productsDeliveredStatus = productsDeliveredStatus;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productsDeliveredStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.productsDeliveredStatusService.update(this.productsDeliveredStatus));
        } else {
            this.subscribeToSaveResponse(this.productsDeliveredStatusService.create(this.productsDeliveredStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProductsDeliveredStatus>>) {
        result.subscribe(
            (res: HttpResponse<IProductsDeliveredStatus>) => this.onSaveSuccess(),
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
