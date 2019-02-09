import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IAmortizacion } from 'app/shared/model/amortizacion.model';
import { AmortizacionService } from './amortizacion.service';
import { ITipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';
import { TipoDeDocumentoDeVentaService } from 'app/entities/tipo-de-documento-de-venta';
import { ITipoDePago } from 'app/shared/model/tipo-de-pago.model';
import { TipoDePagoService } from 'app/entities/tipo-de-pago';

@Component({
    selector: 'jhi-amortizacion-update',
    templateUrl: './amortizacion-update.component.html'
})
export class AmortizacionUpdateComponent implements OnInit {
    amortizacion: IAmortizacion;
    isSaving: boolean;

    tipodedocumentodeventas: ITipoDeDocumentoDeVenta[];

    tipodepagos: ITipoDePago[];
    fechaDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private amortizacionService: AmortizacionService,
        private tipoDeDocumentoDeVentaService: TipoDeDocumentoDeVentaService,
        private tipoDePagoService: TipoDePagoService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ amortizacion }) => {
            this.amortizacion = amortizacion;
        });
        this.tipoDeDocumentoDeVentaService.query().subscribe(
            (res: HttpResponse<ITipoDeDocumentoDeVenta[]>) => {
                this.tipodedocumentodeventas = res.body;
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
        this.dataUtils.clearInputImage(this.amortizacion, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.amortizacion.id !== undefined) {
            this.subscribeToSaveResponse(this.amortizacionService.update(this.amortizacion));
        } else {
            this.subscribeToSaveResponse(this.amortizacionService.create(this.amortizacion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAmortizacion>>) {
        result.subscribe((res: HttpResponse<IAmortizacion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTipoDeDocumentoDeVentaById(index: number, item: ITipoDeDocumentoDeVenta) {
        return item.id;
    }

    trackTipoDePagoById(index: number, item: ITipoDePago) {
        return item.id;
    }
}
