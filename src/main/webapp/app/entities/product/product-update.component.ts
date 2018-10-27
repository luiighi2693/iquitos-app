import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { IUnitMeasurement } from 'app/shared/model/unit-measurement.model';
import { UnitMeasurementService } from 'app/entities/unit-measurement';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { IVariant } from 'app/shared/model/variant.model';
import { VariantService } from 'app/entities/variant';
import { ISellHasProduct } from 'app/shared/model/sell-has-product.model';
import { SellHasProductService } from 'app/entities/sell-has-product';
import { IPurchaseHasProduct } from 'app/shared/model/purchase-has-product.model';
import { PurchaseHasProductService } from 'app/entities/purchase-has-product';

@Component({
    selector: 'jhi-product-update',
    templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
    product: IProduct;
    isSaving: boolean;

    unitmeasurements: IUnitMeasurement[];

    categories: ICategory[];

    variants: IVariant[];

    sellhasproducts: ISellHasProduct[];

    purchasehasproducts: IPurchaseHasProduct[];
    expirationDateDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private productService: ProductService,
        private unitMeasurementService: UnitMeasurementService,
        private categoryService: CategoryService,
        private variantService: VariantService,
        private sellHasProductService: SellHasProductService,
        private purchaseHasProductService: PurchaseHasProductService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ product }) => {
            this.product = product;
        });
        this.unitMeasurementService.query().subscribe(
            (res: HttpResponse<IUnitMeasurement[]>) => {
                this.unitmeasurements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.variantService.query().subscribe(
            (res: HttpResponse<IVariant[]>) => {
                this.variants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.sellHasProductService.query().subscribe(
            (res: HttpResponse<ISellHasProduct[]>) => {
                this.sellhasproducts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.purchaseHasProductService.query().subscribe(
            (res: HttpResponse<IPurchaseHasProduct[]>) => {
                this.purchasehasproducts = res.body;
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
        this.dataUtils.clearInputImage(this.product, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.product.id !== undefined) {
            this.subscribeToSaveResponse(this.productService.update(this.product));
        } else {
            this.subscribeToSaveResponse(this.productService.create(this.product));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
        result.subscribe((res: HttpResponse<IProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUnitMeasurementById(index: number, item: IUnitMeasurement) {
        return item.id;
    }

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    trackVariantById(index: number, item: IVariant) {
        return item.id;
    }

    trackSellHasProductById(index: number, item: ISellHasProduct) {
        return item.id;
    }

    trackPurchaseHasProductById(index: number, item: IPurchaseHasProduct) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
