import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmortization } from 'app/shared/model/amortization.model';

@Component({
    selector: 'jhi-amortization-detail',
    templateUrl: './amortization-detail.component.html'
})
export class AmortizationDetailComponent implements OnInit {
    amortization: IAmortization;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ amortization }) => {
            this.amortization = amortization;
        });
    }

    previousState() {
        window.history.back();
    }
}
