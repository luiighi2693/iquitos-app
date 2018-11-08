import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';
import { TipoDeDocumentoDeVentaService } from './tipo-de-documento-de-venta.service';

@Component({
    selector: 'jhi-tipo-de-documento-de-venta-update',
    templateUrl: './tipo-de-documento-de-venta-update.component.html'
})
export class TipoDeDocumentoDeVentaUpdateComponent implements OnInit {
    tipoDeDocumentoDeVenta: ITipoDeDocumentoDeVenta;
    isSaving: boolean;

    constructor(private tipoDeDocumentoDeVentaService: TipoDeDocumentoDeVentaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoDeDocumentoDeVenta }) => {
            this.tipoDeDocumentoDeVenta = tipoDeDocumentoDeVenta;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoDeDocumentoDeVenta.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoDeDocumentoDeVentaService.update(this.tipoDeDocumentoDeVenta));
        } else {
            this.subscribeToSaveResponse(this.tipoDeDocumentoDeVentaService.create(this.tipoDeDocumentoDeVenta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDeDocumentoDeVenta>>) {
        result.subscribe(
            (res: HttpResponse<ITipoDeDocumentoDeVenta>) => this.onSaveSuccess(),
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
