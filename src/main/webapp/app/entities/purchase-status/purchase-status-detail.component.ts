import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseStatus } from 'app/shared/model/purchase-status.model';

@Component({
    selector: 'jhi-purchase-status-detail',
    templateUrl: './purchase-status-detail.component.html'
})
export class PurchaseStatusDetailComponent implements OnInit {
    purchaseStatus: IPurchaseStatus;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseStatus }) => {
            this.purchaseStatus = purchaseStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
