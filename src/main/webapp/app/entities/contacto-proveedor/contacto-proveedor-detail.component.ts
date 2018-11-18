import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactoProveedor } from 'app/shared/model/contacto-proveedor.model';

@Component({
    selector: 'jhi-contacto-proveedor-detail',
    templateUrl: './contacto-proveedor-detail.component.html'
})
export class ContactoProveedorDetailComponent implements OnInit {
    contactoProveedor: IContactoProveedor;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactoProveedor }) => {
            this.contactoProveedor = contactoProveedor;
        });
    }

    previousState() {
        window.history.back();
    }
}
