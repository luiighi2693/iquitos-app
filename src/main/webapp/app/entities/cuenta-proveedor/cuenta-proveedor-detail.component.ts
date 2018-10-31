import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';

@Component({
    selector: 'jhi-cuenta-proveedor-detail',
    templateUrl: './cuenta-proveedor-detail.component.html'
})
export class CuentaProveedorDetailComponent implements OnInit {
    cuentaProveedor: ICuentaProveedor;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cuentaProveedor }) => {
            this.cuentaProveedor = cuentaProveedor;
        });
    }

    previousState() {
        window.history.back();
    }
}
