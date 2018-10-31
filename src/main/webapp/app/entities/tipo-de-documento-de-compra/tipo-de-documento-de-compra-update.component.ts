import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';
import { TipoDeDocumentoDeCompraService } from './tipo-de-documento-de-compra.service';

@Component({
    selector: 'jhi-tipo-de-documento-de-compra-update',
    templateUrl: './tipo-de-documento-de-compra-update.component.html'
})
export class TipoDeDocumentoDeCompraUpdateComponent implements OnInit {
    tipoDeDocumentoDeCompra: ITipoDeDocumentoDeCompra;
    isSaving: boolean;

    constructor(private tipoDeDocumentoDeCompraService: TipoDeDocumentoDeCompraService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoDeDocumentoDeCompra }) => {
            this.tipoDeDocumentoDeCompra = tipoDeDocumentoDeCompra;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoDeDocumentoDeCompra.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoDeDocumentoDeCompraService.update(this.tipoDeDocumentoDeCompra));
        } else {
            this.subscribeToSaveResponse(this.tipoDeDocumentoDeCompraService.create(this.tipoDeDocumentoDeCompra));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDeDocumentoDeCompra>>) {
        result.subscribe(
            (res: HttpResponse<ITipoDeDocumentoDeCompra>) => this.onSaveSuccess(),
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
