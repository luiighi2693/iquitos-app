import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEstatusDeProductoEntregado } from 'app/shared/model/estatus-de-producto-entregado.model';
import { EstatusDeProductoEntregadoService } from './estatus-de-producto-entregado.service';

@Component({
    selector: 'jhi-estatus-de-producto-entregado-update',
    templateUrl: './estatus-de-producto-entregado-update.component.html'
})
export class EstatusDeProductoEntregadoUpdateComponent implements OnInit {
    estatusDeProductoEntregado: IEstatusDeProductoEntregado;
    isSaving: boolean;

    constructor(private estatusDeProductoEntregadoService: EstatusDeProductoEntregadoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ estatusDeProductoEntregado }) => {
            this.estatusDeProductoEntregado = estatusDeProductoEntregado;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.estatusDeProductoEntregado.id !== undefined) {
            this.subscribeToSaveResponse(this.estatusDeProductoEntregadoService.update(this.estatusDeProductoEntregado));
        } else {
            this.subscribeToSaveResponse(this.estatusDeProductoEntregadoService.create(this.estatusDeProductoEntregado));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEstatusDeProductoEntregado>>) {
        result.subscribe(
            (res: HttpResponse<IEstatusDeProductoEntregado>) => this.onSaveSuccess(),
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
