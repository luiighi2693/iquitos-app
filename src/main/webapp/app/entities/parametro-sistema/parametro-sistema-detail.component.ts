import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParametroSistema } from 'app/shared/model/parametro-sistema.model';

@Component({
    selector: 'jhi-parametro-sistema-detail',
    templateUrl: './parametro-sistema-detail.component.html'
})
export class ParametroSistemaDetailComponent implements OnInit {
    parametroSistema: IParametroSistema;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parametroSistema }) => {
            this.parametroSistema = parametroSistema;
        });
    }

    previousState() {
        window.history.back();
    }
}
