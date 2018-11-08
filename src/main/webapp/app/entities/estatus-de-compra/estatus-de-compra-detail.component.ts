import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstatusDeCompra } from 'app/shared/model/estatus-de-compra.model';

@Component({
    selector: 'jhi-estatus-de-compra-detail',
    templateUrl: './estatus-de-compra-detail.component.html'
})
export class EstatusDeCompraDetailComponent implements OnInit {
    estatusDeCompra: IEstatusDeCompra;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estatusDeCompra }) => {
            this.estatusDeCompra = estatusDeCompra;
        });
    }

    previousState() {
        window.history.back();
    }
}
