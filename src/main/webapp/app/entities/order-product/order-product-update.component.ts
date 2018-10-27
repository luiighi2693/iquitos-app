import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOrderProduct } from 'app/shared/model/order-product.model';
import { OrderProductService } from './order-product.service';
import { IProvider } from 'app/shared/model/provider.model';
import { ProviderService } from 'app/entities/provider';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-order-product-update',
    templateUrl: './order-product-update.component.html'
})
export class OrderProductUpdateComponent implements OnInit {
    orderProduct: IOrderProduct;
    isSaving: boolean;

    providers: IProvider[];

    products: IProduct[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private orderProductService: OrderProductService,
        private providerService: ProviderService,
        private productService: ProductService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orderProduct }) => {
            this.orderProduct = orderProduct;
        });
        this.providerService.query({ filter: 'orderproduct-is-null' }).subscribe(
            (res: HttpResponse<IProvider[]>) => {
                if (!this.orderProduct.providerId) {
                    this.providers = res.body;
                } else {
                    this.providerService.find(this.orderProduct.providerId).subscribe(
                        (subRes: HttpResponse<IProvider>) => {
                            this.providers = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.orderProduct.id !== undefined) {
            this.subscribeToSaveResponse(this.orderProductService.update(this.orderProduct));
        } else {
            this.subscribeToSaveResponse(this.orderProductService.create(this.orderProduct));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrderProduct>>) {
        result.subscribe((res: HttpResponse<IOrderProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProviderById(index: number, item: IProvider) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
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
