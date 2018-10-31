import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoDePago } from 'app/shared/model/tipo-de-pago.model';
import { TipoDePagoService } from './tipo-de-pago.service';

@Component({
    selector: 'jhi-tipo-de-pago-update',
    templateUrl: './tipo-de-pago-update.component.html'
})
export class TipoDePagoUpdateComponent implements OnInit {
    tipoDePago: ITipoDePago;
    isSaving: boolean;

    constructor(private tipoDePagoService: TipoDePagoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoDePago }) => {
            this.tipoDePago = tipoDePago;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoDePago.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoDePagoService.update(this.tipoDePago));
        } else {
            this.subscribeToSaveResponse(this.tipoDePagoService.create(this.tipoDePago));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDePago>>) {
        result.subscribe((res: HttpResponse<ITipoDePago>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
