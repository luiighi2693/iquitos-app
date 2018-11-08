import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';

@Component({
    selector: 'jhi-tipo-de-documento-de-compra-detail',
    templateUrl: './tipo-de-documento-de-compra-detail.component.html'
})
export class TipoDeDocumentoDeCompraDetailComponent implements OnInit {
    tipoDeDocumentoDeCompra: ITipoDeDocumentoDeCompra;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeDocumentoDeCompra }) => {
            this.tipoDeDocumentoDeCompra = tipoDeDocumentoDeCompra;
        });
    }

    previousState() {
        window.history.back();
    }
}
