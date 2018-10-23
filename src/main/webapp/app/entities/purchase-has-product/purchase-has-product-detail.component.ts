import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseHasProduct } from 'app/shared/model/purchase-has-product.model';

@Component({
    selector: 'jhi-purchase-has-product-detail',
    templateUrl: './purchase-has-product-detail.component.html'
})
export class PurchaseHasProductDetailComponent implements OnInit {
    purchaseHasProduct: IPurchaseHasProduct;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseHasProduct }) => {
            this.purchaseHasProduct = purchaseHasProduct;
        });
    }

    previousState() {
        window.history.back();
    }
}
