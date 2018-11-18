import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUnidadDeMedida } from 'app/shared/model/unidad-de-medida.model';
import { UnidadDeMedidaService } from './unidad-de-medida.service';

@Component({
    selector: 'jhi-unidad-de-medida-update',
    templateUrl: './unidad-de-medida-update.component.html'
})
export class UnidadDeMedidaUpdateComponent implements OnInit {
    unidadDeMedida: IUnidadDeMedida;
    isSaving: boolean;

    constructor(private unidadDeMedidaService: UnidadDeMedidaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ unidadDeMedida }) => {
            this.unidadDeMedida = unidadDeMedida;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.unidadDeMedida.id !== undefined) {
            this.subscribeToSaveResponse(this.unidadDeMedidaService.update(this.unidadDeMedida));
        } else {
            this.subscribeToSaveResponse(this.unidadDeMedidaService.create(this.unidadDeMedida));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUnidadDeMedida>>) {
        result.subscribe((res: HttpResponse<IUnidadDeMedida>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
