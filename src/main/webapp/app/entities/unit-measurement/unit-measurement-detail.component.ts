import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnitMeasurement } from 'app/shared/model/unit-measurement.model';

@Component({
    selector: 'jhi-unit-measurement-detail',
    templateUrl: './unit-measurement-detail.component.html'
})
export class UnitMeasurementDetailComponent implements OnInit {
    unitMeasurement: IUnitMeasurement;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ unitMeasurement }) => {
            this.unitMeasurement = unitMeasurement;
        });
    }

    previousState() {
        window.history.back();
    }
}
