import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { ICuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';
import { CuentaProveedorService } from 'app/entities/cuenta-proveedor';
import { IContactoProveedor } from 'app/shared/model/contacto-proveedor.model';
import { ContactoProveedorService } from 'app/entities/contacto-proveedor';

@Component({
    selector: 'jhi-proveedor-update',
    templateUrl: './proveedor-update.component.html'
})
export class ProveedorUpdateComponent implements OnInit {
    proveedor: IProveedor;
    isSaving: boolean;

    cuentaproveedors: ICuentaProveedor[];

    contactoproveedors: IContactoProveedor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private proveedorService: ProveedorService,
        private cuentaProveedorService: CuentaProveedorService,
        private contactoProveedorService: ContactoProveedorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ proveedor }) => {
            this.proveedor = proveedor;
        });
        this.cuentaProveedorService.query().subscribe(
            (res: HttpResponse<ICuentaProveedor[]>) => {
                this.cuentaproveedors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.contactoProveedorService.query().subscribe(
            (res: HttpResponse<IContactoProveedor[]>) => {
                this.contactoproveedors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCuentaProveedorById(index: number, item: ICuentaProveedor) {
        return item.id;
    }

    trackContactoProveedorById(index: number, item: IContactoProveedor) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
