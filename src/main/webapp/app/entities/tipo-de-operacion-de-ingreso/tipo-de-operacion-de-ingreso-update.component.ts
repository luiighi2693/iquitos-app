import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoDeOperacionDeIngreso } from 'app/shared/model/tipo-de-operacion-de-ingreso.model';
import { TipoDeOperacionDeIngresoService } from './tipo-de-operacion-de-ingreso.service';

@Component({
    selector: 'jhi-tipo-de-operacion-de-ingreso-update',
    templateUrl: './tipo-de-operacion-de-ingreso-update.component.html'
})
export class TipoDeOperacionDeIngresoUpdateComponent implements OnInit {
    tipoDeOperacionDeIngreso: ITipoDeOperacionDeIngreso;
    isSaving: boolean;

    constructor(private tipoDeOperacionDeIngresoService: TipoDeOperacionDeIngresoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoDeOperacionDeIngreso }) => {
            this.tipoDeOperacionDeIngreso = tipoDeOperacionDeIngreso;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoDeOperacionDeIngreso.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoDeOperacionDeIngresoService.update(this.tipoDeOperacionDeIngreso));
        } else {
            this.subscribeToSaveResponse(this.tipoDeOperacionDeIngresoService.create(this.tipoDeOperacionDeIngreso));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDeOperacionDeIngreso>>) {
        result.subscribe(
            (res: HttpResponse<ITipoDeOperacionDeIngreso>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
