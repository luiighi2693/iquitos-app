import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IContactoProveedor } from 'app/shared/model/contacto-proveedor.model';
import { ContactoProveedorService } from './contacto-proveedor.service';

@Component({
    selector: 'jhi-contacto-proveedor-update',
    templateUrl: './contacto-proveedor-update.component.html'
})
export class ContactoProveedorUpdateComponent implements OnInit {
    contactoProveedor: IContactoProveedor;
    isSaving: boolean;

    constructor(private contactoProveedorService: ContactoProveedorService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contactoProveedor }) => {
            this.contactoProveedor = contactoProveedor;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contactoProveedor.id !== undefined) {
            this.subscribeToSaveResponse(this.contactoProveedorService.update(this.contactoProveedor));
        } else {
            this.subscribeToSaveResponse(this.contactoProveedorService.create(this.contactoProveedor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IContactoProveedor>>) {
        result.subscribe((res: HttpResponse<IContactoProveedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
