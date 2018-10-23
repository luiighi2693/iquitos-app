import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProviderPayment } from 'app/shared/model/provider-payment.model';

@Component({
    selector: 'jhi-provider-payment-detail',
    templateUrl: './provider-payment-detail.component.html'
})
export class ProviderPaymentDetailComponent implements OnInit {
    providerPayment: IProviderPayment;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ providerPayment }) => {
            this.providerPayment = providerPayment;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
