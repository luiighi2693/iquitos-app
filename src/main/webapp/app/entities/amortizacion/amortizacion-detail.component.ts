import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmortizacion } from 'app/shared/model/amortizacion.model';

@Component({
    selector: 'jhi-amortizacion-detail',
    templateUrl: './amortizacion-detail.component.html'
})
export class AmortizacionDetailComponent implements OnInit {
    amortizacion: IAmortizacion;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ amortizacion }) => {
            this.amortizacion = amortizacion;
        });
    }

    previousState() {
        window.history.back();
    }
}
