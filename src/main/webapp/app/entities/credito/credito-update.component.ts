import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ICredito } from 'app/shared/model/credito.model';
import { CreditoService } from './credito.service';
import { IVenta } from 'app/shared/model/venta.model';
import { VentaService } from 'app/entities/venta';
import { ICompra } from 'app/shared/model/compra.model';
import { CompraService } from 'app/entities/compra';

@Component({
    selector: 'jhi-credito-update',
    templateUrl: './credito-update.component.html'
})
export class CreditoUpdateComponent implements OnInit {
    credito: ICredito;
    isSaving: boolean;

    ventas: IVenta[];

    compras: ICompra[];
    fechaDp: any;
    fechaLimiteDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private creditoService: CreditoService,
        private ventaService: VentaService,
        private compraService: CompraService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ credito }) => {
            this.credito = credito;
        });
        this.ventaService.query().subscribe(
            (res: HttpResponse<IVenta[]>) => {
                this.ventas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.compraService.query().subscribe(
            (res: HttpResponse<ICompra[]>) => {
                this.compras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.credito.id !== undefined) {
            this.subscribeToSaveResponse(this.creditoService.update(this.credito));
        } else {
            this.subscribeToSaveResponse(this.creditoService.create(this.credito));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICredito>>) {
        result.subscribe((res: HttpResponse<ICredito>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVentaById(index: number, item: IVenta) {
        return item.id;
    }

    trackCompraById(index: number, item: ICompra) {
        return item.id;
    }
}
