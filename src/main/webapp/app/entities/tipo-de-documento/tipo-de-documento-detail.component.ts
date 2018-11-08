import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoDeDocumento } from 'app/shared/model/tipo-de-documento.model';

@Component({
    selector: 'jhi-tipo-de-documento-detail',
    templateUrl: './tipo-de-documento-detail.component.html'
})
export class TipoDeDocumentoDetailComponent implements OnInit {
    tipoDeDocumento: ITipoDeDocumento;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeDocumento }) => {
            this.tipoDeDocumento = tipoDeDocumento;
        });
    }

    previousState() {
        window.history.back();
    }
}
