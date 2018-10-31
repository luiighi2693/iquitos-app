import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoDeOperacionDeGasto } from 'app/shared/model/tipo-de-operacion-de-gasto.model';
import { TipoDeOperacionDeGastoService } from './tipo-de-operacion-de-gasto.service';

@Component({
    selector: 'jhi-tipo-de-operacion-de-gasto-update',
    templateUrl: './tipo-de-operacion-de-gasto-update.component.html'
})
export class TipoDeOperacionDeGastoUpdateComponent implements OnInit {
    tipoDeOperacionDeGasto: ITipoDeOperacionDeGasto;
    isSaving: boolean;

    constructor(private tipoDeOperacionDeGastoService: TipoDeOperacionDeGastoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoDeOperacionDeGasto }) => {
            this.tipoDeOperacionDeGasto = tipoDeOperacionDeGasto;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoDeOperacionDeGasto.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoDeOperacionDeGastoService.update(this.tipoDeOperacionDeGasto));
        } else {
            this.subscribeToSaveResponse(this.tipoDeOperacionDeGastoService.create(this.tipoDeOperacionDeGasto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDeOperacionDeGasto>>) {
        result.subscribe(
            (res: HttpResponse<ITipoDeOperacionDeGasto>) => this.onSaveSuccess(),
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
