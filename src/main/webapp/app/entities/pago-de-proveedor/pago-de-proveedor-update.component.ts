import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPagoDeProveedor } from 'app/shared/model/pago-de-proveedor.model';
import { PagoDeProveedorService } from './pago-de-proveedor.service';
import { ITipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';
import { TipoDeDocumentoDeCompraService } from 'app/entities/tipo-de-documento-de-compra';
import { ITipoDePago } from 'app/shared/model/tipo-de-pago.model';
import { TipoDePagoService } from 'app/entities/tipo-de-pago';

@Component({
    selector: 'jhi-pago-de-proveedor-update',
    templateUrl: './pago-de-proveedor-update.component.html'
})
export class PagoDeProveedorUpdateComponent implements OnInit {
    pagoDeProveedor: IPagoDeProveedor;
    isSaving: boolean;

    tipodedocumentodecompras: ITipoDeDocumentoDeCompra[];

    tipodepagos: ITipoDePago[];
    fechaDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private pagoDeProveedorService: PagoDeProveedorService,
        private tipoDeDocumentoDeCompraService: TipoDeDocumentoDeCompraService,
        private tipoDePagoService: TipoDePagoService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pagoDeProveedor }) => {
            this.pagoDeProveedor = pagoDeProveedor;
        });
        this.tipoDeDocumentoDeCompraService.query().subscribe(
            (res: HttpResponse<ITipoDeDocumentoDeCompra[]>) => {
                this.tipodedocumentodecompras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoDePagoService.query().subscribe(
            (res: HttpResponse<ITipoDePago[]>) => {
                this.tipodepagos = res.body;
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
        this.dataUtils.clearInputImage(this.pagoDeProveedor, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pagoDeProveedor.id !== undefined) {
            this.subscribeToSaveResponse(this.pagoDeProveedorService.update(this.pagoDeProveedor));
        } else {
            this.subscribeToSaveResponse(this.pagoDeProveedorService.create(this.pagoDeProveedor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPagoDeProveedor>>) {
        result.subscribe((res: HttpResponse<IPagoDeProveedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTipoDeDocumentoDeCompraById(index: number, item: ITipoDeDocumentoDeCompra) {
        return item.id;
    }

    trackTipoDePagoById(index: number, item: ITipoDePago) {
        return item.id;
    }
}
