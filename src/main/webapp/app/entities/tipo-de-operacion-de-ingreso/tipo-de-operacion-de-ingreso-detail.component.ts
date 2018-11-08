import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoDeOperacionDeIngreso } from 'app/shared/model/tipo-de-operacion-de-ingreso.model';

@Component({
    selector: 'jhi-tipo-de-operacion-de-ingreso-detail',
    templateUrl: './tipo-de-operacion-de-ingreso-detail.component.html'
})
export class TipoDeOperacionDeIngresoDetailComponent implements OnInit {
    tipoDeOperacionDeIngreso: ITipoDeOperacionDeIngreso;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeOperacionDeIngreso }) => {
            this.tipoDeOperacionDeIngreso = tipoDeOperacionDeIngreso;
        });
    }

    previousState() {
        window.history.back();
    }
}
