import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmpleado } from 'app/shared/model/empleado.model';

@Component({
    selector: 'jhi-empleado-detail',
    templateUrl: './empleado-detail.component.html'
})
export class EmpleadoDetailComponent implements OnInit {
    empleado: IEmpleado;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ empleado }) => {
            this.empleado = empleado;
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
