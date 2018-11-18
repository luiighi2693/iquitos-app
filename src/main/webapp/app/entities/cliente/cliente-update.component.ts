import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IUsuarioExterno } from 'app/shared/model/usuario-externo.model';
import { UsuarioExternoService } from 'app/entities/usuario-externo';
import { ITipoDeDocumento } from 'app/shared/model/tipo-de-documento.model';
import { TipoDeDocumentoService } from 'app/entities/tipo-de-documento';

@Component({
    selector: 'jhi-cliente-update',
    templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
    cliente: ICliente;
    isSaving: boolean;

    usuarios: IUsuarioExterno[];

    tipodedocumentos: ITipoDeDocumento[];
    fechaDeNacimientoDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private clienteService: ClienteService,
        private usuarioExternoService: UsuarioExternoService,
        private tipoDeDocumentoService: TipoDeDocumentoService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cliente }) => {
            this.cliente = cliente;
        });
        this.usuarioExternoService.query({ filter: 'cliente-is-null' }).subscribe(
            (res: HttpResponse<IUsuarioExterno[]>) => {
                if (!this.cliente.usuarioId) {
                    this.usuarios = res.body;
                } else {
                    this.usuarioExternoService.find(this.cliente.usuarioId).subscribe(
                        (subRes: HttpResponse<IUsuarioExterno>) => {
                            this.usuarios = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoDeDocumentoService.query().subscribe(
            (res: HttpResponse<ITipoDeDocumento[]>) => {
                this.tipodedocumentos = res.body;
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
        this.dataUtils.clearInputImage(this.cliente, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cliente.id !== undefined) {
            this.subscribeToSaveResponse(this.clienteService.update(this.cliente));
        } else {
            this.subscribeToSaveResponse(this.clienteService.create(this.cliente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
        result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTipoDeDocumentoById(index: number, item: ITipoDeDocumento) {
        return item.id;
    }
}
