import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderProduct } from 'app/shared/model/order-product.model';

@Component({
    selector: 'jhi-order-product-detail',
    templateUrl: './order-product-detail.component.html'
})
export class OrderProductDetailComponent implements OnInit {
    orderProduct: IOrderProduct;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderProduct }) => {
            this.orderProduct = orderProduct;
        });
    }

    previousState() {
        window.history.back();
    }
}
