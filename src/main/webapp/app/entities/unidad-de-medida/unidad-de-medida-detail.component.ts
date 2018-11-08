import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnidadDeMedida } from 'app/shared/model/unidad-de-medida.model';

@Component({
    selector: 'jhi-unidad-de-medida-detail',
    templateUrl: './unidad-de-medida-detail.component.html'
})
export class UnidadDeMedidaDetailComponent implements OnInit {
    unidadDeMedida: IUnidadDeMedida;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ unidadDeMedida }) => {
            this.unidadDeMedida = unidadDeMedida;
        });
    }

    previousState() {
        window.history.back();
    }
}
