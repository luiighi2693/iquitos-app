import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPagoDeProveedor } from 'app/shared/model/pago-de-proveedor.model';

@Component({
    selector: 'jhi-pago-de-proveedor-detail',
    templateUrl: './pago-de-proveedor-detail.component.html'
})
export class PagoDeProveedorDetailComponent implements OnInit {
    pagoDeProveedor: IPagoDeProveedor;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pagoDeProveedor }) => {
            this.pagoDeProveedor = pagoDeProveedor;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
