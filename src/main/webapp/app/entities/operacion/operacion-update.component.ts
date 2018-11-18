import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IOperacion } from 'app/shared/model/operacion.model';
import { OperacionService } from './operacion.service';
import { ICaja } from 'app/shared/model/caja.model';
import { CajaService } from 'app/entities/caja';
import { ITipoDePago } from 'app/shared/model/tipo-de-pago.model';
import { TipoDePagoService } from 'app/entities/tipo-de-pago';
import { ITipoDeOperacionDeIngreso } from 'app/shared/model/tipo-de-operacion-de-ingreso.model';
import { TipoDeOperacionDeIngresoService } from 'app/entities/tipo-de-operacion-de-ingreso';
import { ITipoDeOperacionDeGasto } from 'app/shared/model/tipo-de-operacion-de-gasto.model';
import { TipoDeOperacionDeGastoService } from 'app/entities/tipo-de-operacion-de-gasto';

@Component({
    selector: 'jhi-operacion-update',
    templateUrl: './operacion-update.component.html'
})
export class OperacionUpdateComponent implements OnInit {
    operacion: IOperacion;
    isSaving: boolean;

    cajas: ICaja[];

    tipodepagos: ITipoDePago[];

    tipodeoperaciondeingresos: ITipoDeOperacionDeIngreso[];

    tipodeoperaciondegastos: ITipoDeOperacionDeGasto[];
    fechaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private operacionService: OperacionService,
        private cajaService: CajaService,
        private tipoDePagoService: TipoDePagoService,
        private tipoDeOperacionDeIngresoService: TipoDeOperacionDeIngresoService,
        private tipoDeOperacionDeGastoService: TipoDeOperacionDeGastoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ operacion }) => {
            this.operacion = operacion;
        });
        this.cajaService.query().subscribe(
            (res: HttpResponse<ICaja[]>) => {
                this.cajas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoDePagoService.query().subscribe(
            (res: HttpResponse<ITipoDePago[]>) => {
                this.tipodepagos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoDeOperacionDeIngresoService.query().subscribe(
            (res: HttpResponse<ITipoDeOperacionDeIngreso[]>) => {
                this.tipodeoperaciondeingresos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoDeOperacionDeGastoService.query().subscribe(
            (res: HttpResponse<ITipoDeOperacionDeGasto[]>) => {
                this.tipodeoperaciondegastos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.operacion.id !== undefined) {
            this.subscribeToSaveResponse(this.operacionService.update(this.operacion));
        } else {
            this.subscribeToSaveResponse(this.operacionService.create(this.operacion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOperacion>>) {
        result.subscribe((res: HttpResponse<IOperacion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCajaById(index: number, item: ICaja) {
        return item.id;
    }

    trackTipoDePagoById(index: number, item: ITipoDePago) {
        return item.id;
    }

    trackTipoDeOperacionDeIngresoById(index: number, item: ITipoDeOperacionDeIngreso) {
        return item.id;
    }

    trackTipoDeOperacionDeGastoById(index: number, item: ITipoDeOperacionDeGasto) {
        return item.id;
    }
}
