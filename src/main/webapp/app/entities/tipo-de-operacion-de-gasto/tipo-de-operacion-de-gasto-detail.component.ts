import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoDeOperacionDeGasto } from 'app/shared/model/tipo-de-operacion-de-gasto.model';

@Component({
    selector: 'jhi-tipo-de-operacion-de-gasto-detail',
    templateUrl: './tipo-de-operacion-de-gasto-detail.component.html'
})
export class TipoDeOperacionDeGastoDetailComponent implements OnInit {
    tipoDeOperacionDeGasto: ITipoDeOperacionDeGasto;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeOperacionDeGasto }) => {
            this.tipoDeOperacionDeGasto = tipoDeOperacionDeGasto;
        });
    }

    previousState() {
        window.history.back();
    }
}
