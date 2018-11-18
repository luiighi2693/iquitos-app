import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstatusDeProductoEntregado } from 'app/shared/model/estatus-de-producto-entregado.model';

@Component({
    selector: 'jhi-estatus-de-producto-entregado-detail',
    templateUrl: './estatus-de-producto-entregado-detail.component.html'
})
export class EstatusDeProductoEntregadoDetailComponent implements OnInit {
    estatusDeProductoEntregado: IEstatusDeProductoEntregado;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estatusDeProductoEntregado }) => {
            this.estatusDeProductoEntregado = estatusDeProductoEntregado;
        });
    }

    previousState() {
        window.history.back();
    }
}
