import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';

@Component({
    selector: 'jhi-tipo-de-documento-de-venta-detail',
    templateUrl: './tipo-de-documento-de-venta-detail.component.html'
})
export class TipoDeDocumentoDeVentaDetailComponent implements OnInit {
    tipoDeDocumentoDeVenta: ITipoDeDocumentoDeVenta;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeDocumentoDeVenta }) => {
            this.tipoDeDocumentoDeVenta = tipoDeDocumentoDeVenta;
        });
    }

    previousState() {
        window.history.back();
    }
}
