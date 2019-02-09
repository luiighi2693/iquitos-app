import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAmortizacion } from 'app/shared/model/amortizacion.model';

@Component({
    selector: 'jhi-amortizacion-detail',
    templateUrl: './amortizacion-detail.component.html'
})
export class AmortizacionDetailComponent implements OnInit {
    amortizacion: IAmortizacion;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ amortizacion }) => {
            this.amortizacion = amortizacion;
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
