import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEstatusDeCompra } from 'app/shared/model/estatus-de-compra.model';
import { EstatusDeCompraService } from './estatus-de-compra.service';

@Component({
    selector: 'jhi-estatus-de-compra-update',
    templateUrl: './estatus-de-compra-update.component.html'
})
export class EstatusDeCompraUpdateComponent implements OnInit {
    estatusDeCompra: IEstatusDeCompra;
    isSaving: boolean;

    constructor(private estatusDeCompraService: EstatusDeCompraService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ estatusDeCompra }) => {
            this.estatusDeCompra = estatusDeCompra;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.estatusDeCompra.id !== undefined) {
            this.subscribeToSaveResponse(this.estatusDeCompraService.update(this.estatusDeCompra));
        } else {
            this.subscribeToSaveResponse(this.estatusDeCompraService.create(this.estatusDeCompra));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEstatusDeCompra>>) {
        result.subscribe((res: HttpResponse<IEstatusDeCompra>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
