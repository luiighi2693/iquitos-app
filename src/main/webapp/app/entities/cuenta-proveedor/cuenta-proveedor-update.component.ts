import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';
import { CuentaProveedorService } from './cuenta-proveedor.service';

@Component({
    selector: 'jhi-cuenta-proveedor-update',
    templateUrl: './cuenta-proveedor-update.component.html'
})
export class CuentaProveedorUpdateComponent implements OnInit {
    cuentaProveedor: ICuentaProveedor;
    isSaving: boolean;

    constructor(private cuentaProveedorService: CuentaProveedorService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cuentaProveedor }) => {
            this.cuentaProveedor = cuentaProveedor;
        });
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
}
