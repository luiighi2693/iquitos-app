import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ICuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';
import { CuentaProveedorService } from './cuenta-proveedor.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';

@Component({
    selector: 'jhi-cuenta-proveedor-update',
    templateUrl: './cuenta-proveedor-update.component.html'
})
export class CuentaProveedorUpdateComponent implements OnInit {
    cuentaProveedor: ICuentaProveedor;
    isSaving: boolean;

    proveedors: IProveedor[];
    fechaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private cuentaProveedorService: CuentaProveedorService,
        private proveedorService: ProveedorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cuentaProveedor }) => {
            this.cuentaProveedor = cuentaProveedor;
        });
        this.proveedorService.query().subscribe(
            (res: HttpResponse<IProveedor[]>) => {
                this.proveedors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cuentaProveedor.id !== undefined) {
            this.subscribeToSaveResponse(this.cuentaProveedorService.update(this.cuentaProveedor));
        } else {
            this.subscribeToSaveResponse(this.cuentaProveedorService.create(this.cuentaProveedor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICuentaProveedor>>) {
        result.subscribe((res: HttpResponse<ICuentaProveedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProveedorById(index: number, item: IProveedor) {
        return item.id;
    }
}
