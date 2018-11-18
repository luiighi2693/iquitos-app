import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IEmpleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from './empleado.service';
import { IUsuarioExterno } from 'app/shared/model/usuario-externo.model';
import { UsuarioExternoService } from 'app/entities/usuario-externo';

@Component({
    selector: 'jhi-empleado-update',
    templateUrl: './empleado-update.component.html'
})
export class EmpleadoUpdateComponent implements OnInit {
    empleado: IEmpleado;
    isSaving: boolean;

    usuarios: IUsuarioExterno[];
    fechaDeNacimientoDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private empleadoService: EmpleadoService,
        private usuarioExternoService: UsuarioExternoService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ empleado }) => {
            this.empleado = empleado;
        });
        this.usuarioExternoService.query({ filter: 'empleado-is-null' }).subscribe(
            (res: HttpResponse<IUsuarioExterno[]>) => {
                if (!this.empleado.usuarioId) {
                    this.usuarios = res.body;
                } else {
                    this.usuarioExternoService.find(this.empleado.usuarioId).subscribe(
                        (subRes: HttpResponse<IUsuarioExterno>) => {
                            this.usuarios = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.empleado, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.empleado.id !== undefined) {
            this.subscribeToSaveResponse(this.empleadoService.update(this.empleado));
        } else {
            this.subscribeToSaveResponse(this.empleadoService.create(this.empleado));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleado>>) {
        result.subscribe((res: HttpResponse<IEmpleado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUsuarioExternoById(index: number, item: IUsuarioExterno) {
        return item.id;
    }
}
