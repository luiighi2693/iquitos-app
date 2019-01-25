import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IParametroSistema } from 'app/shared/model/parametro-sistema.model';
import { ParametroSistemaService } from './parametro-sistema.service';

@Component({
    selector: 'jhi-parametro-sistema-update',
    templateUrl: './parametro-sistema-update.component.html'
})
export class ParametroSistemaUpdateComponent implements OnInit {
    parametroSistema: IParametroSistema;
    isSaving: boolean;

    constructor(private parametroSistemaService: ParametroSistemaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ parametroSistema }) => {
            this.parametroSistema = parametroSistema;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.parametroSistema.id !== undefined) {
            this.subscribeToSaveResponse(this.parametroSistemaService.update(this.parametroSistema));
        } else {
            this.subscribeToSaveResponse(this.parametroSistemaService.create(this.parametroSistema));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IParametroSistema>>) {
        result.subscribe((res: HttpResponse<IParametroSistema>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
