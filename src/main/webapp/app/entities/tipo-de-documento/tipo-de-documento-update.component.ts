import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoDeDocumento } from 'app/shared/model/tipo-de-documento.model';
import { TipoDeDocumentoService } from './tipo-de-documento.service';

@Component({
    selector: 'jhi-tipo-de-documento-update',
    templateUrl: './tipo-de-documento-update.component.html'
})
export class TipoDeDocumentoUpdateComponent implements OnInit {
    tipoDeDocumento: ITipoDeDocumento;
    isSaving: boolean;

    constructor(private tipoDeDocumentoService: TipoDeDocumentoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoDeDocumento }) => {
            this.tipoDeDocumento = tipoDeDocumento;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoDeDocumento.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoDeDocumentoService.update(this.tipoDeDocumento));
        } else {
            this.subscribeToSaveResponse(this.tipoDeDocumentoService.create(this.tipoDeDocumento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDeDocumento>>) {
        result.subscribe((res: HttpResponse<ITipoDeDocumento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
