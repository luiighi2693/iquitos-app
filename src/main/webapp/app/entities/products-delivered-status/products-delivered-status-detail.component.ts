import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductsDeliveredStatus } from 'app/shared/model/products-delivered-status.model';

@Component({
    selector: 'jhi-products-delivered-status-detail',
    templateUrl: './products-delivered-status-detail.component.html'
})
export class ProductsDeliveredStatusDetailComponent implements OnInit {
    productsDeliveredStatus: IProductsDeliveredStatus;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productsDeliveredStatus }) => {
            this.productsDeliveredStatus = productsDeliveredStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
