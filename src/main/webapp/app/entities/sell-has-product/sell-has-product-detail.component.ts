import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISellHasProduct } from 'app/shared/model/sell-has-product.model';

@Component({
    selector: 'jhi-sell-has-product-detail',
    templateUrl: './sell-has-product-detail.component.html'
})
export class SellHasProductDetailComponent implements OnInit {
    sellHasProduct: ISellHasProduct;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sellHasProduct }) => {
            this.sellHasProduct = sellHasProduct;
        });
    }

    previousState() {
        window.history.back();
    }
}
