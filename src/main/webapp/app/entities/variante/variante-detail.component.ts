import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVariante } from 'app/shared/model/variante.model';

@Component({
    selector: 'jhi-variante-detail',
    templateUrl: './variante-detail.component.html'
})
export class VarianteDetailComponent implements OnInit {
    variante: IVariante;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ variante }) => {
            this.variante = variante;
        });
    }

    previousState() {
        window.history.back();
    }
}
