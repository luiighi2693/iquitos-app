import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from './proveedor.service';

@Component({
    selector: 'jhi-proveedor-update',
    templateUrl: './proveedor-update.component.html'
})
export class ProveedorUpdateComponent implements OnInit {
    proveedor: IProveedor;
    isSaving: boolean;

    constructor(private proveedorService: ProveedorService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ proveedor }) => {
            this.proveedor = proveedor;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.proveedor.id !== undefined) {
            this.subscribeToSaveResponse(this.proveedorService.update(this.proveedor));
        } else {
            this.subscribeToSaveResponse(this.proveedorService.create(this.proveedor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>) {
        result.subscribe((res: HttpResponse<IProveedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
