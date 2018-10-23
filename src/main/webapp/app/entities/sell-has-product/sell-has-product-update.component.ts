import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISellHasProduct } from 'app/shared/model/sell-has-product.model';
import { SellHasProductService } from './sell-has-product.service';
import { ISell } from 'app/shared/model/sell.model';
import { SellService } from 'app/entities/sell';
import { IVariant } from 'app/shared/model/variant.model';
import { VariantService } from 'app/entities/variant';

@Component({
    selector: 'jhi-sell-has-product-update',
    templateUrl: './sell-has-product-update.component.html'
})
export class SellHasProductUpdateComponent implements OnInit {
    sellHasProduct: ISellHasProduct;
    isSaving: boolean;

    sells: ISell[];

    variants: IVariant[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private sellHasProductService: SellHasProductService,
        private sellService: SellService,
        private variantService: VariantService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sellHasProduct }) => {
            this.sellHasProduct = sellHasProduct;
        });
        this.sellService.query({ filter: 'sellhasproduct-is-null' }).subscribe(
            (res: HttpResponse<ISell[]>) => {
                if (!this.sellHasProduct.sellId) {
                    this.sells = res.body;
                } else {
                    this.sellService.find(this.sellHasProduct.sellId).subscribe(
                        (subRes: HttpResponse<ISell>) => {
                            this.sells = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.variantService.query({ filter: 'sellhasproduct-is-null' }).subscribe(
            (res: HttpResponse<IVariant[]>) => {
                if (!this.sellHasProduct.variantId) {
                    this.variants = res.body;
                } else {
                    this.variantService.find(this.sellHasProduct.variantId).subscribe(
                        (subRes: HttpResponse<IVariant>) => {
                            this.variants = [subRes.body].concat(res.body);
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
        if (this.sellHasProduct.id !== undefined) {
            this.subscribeToSaveResponse(this.sellHasProductService.update(this.sellHasProduct));
        } else {
            this.subscribeToSaveResponse(this.sellHasProductService.create(this.sellHasProduct));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISellHasProduct>>) {
        result.subscribe((res: HttpResponse<ISellHasProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVariantById(index: number, item: IVariant) {
        return item.id;
    }
}
