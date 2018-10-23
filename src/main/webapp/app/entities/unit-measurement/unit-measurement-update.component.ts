import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUnitMeasurement } from 'app/shared/model/unit-measurement.model';
import { UnitMeasurementService } from './unit-measurement.service';

@Component({
    selector: 'jhi-unit-measurement-update',
    templateUrl: './unit-measurement-update.component.html'
})
export class UnitMeasurementUpdateComponent implements OnInit {
    unitMeasurement: IUnitMeasurement;
    isSaving: boolean;

    constructor(private unitMeasurementService: UnitMeasurementService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ unitMeasurement }) => {
            this.unitMeasurement = unitMeasurement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.unitMeasurement.id !== undefined) {
            this.subscribeToSaveResponse(this.unitMeasurementService.update(this.unitMeasurement));
        } else {
            this.subscribeToSaveResponse(this.unitMeasurementService.create(this.unitMeasurement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUnitMeasurement>>) {
        result.subscribe((res: HttpResponse<IUnitMeasurement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
