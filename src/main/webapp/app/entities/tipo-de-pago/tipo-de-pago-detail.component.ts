import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoDePago } from 'app/shared/model/tipo-de-pago.model';

@Component({
    selector: 'jhi-tipo-de-pago-detail',
    templateUrl: './tipo-de-pago-detail.component.html'
})
export class TipoDePagoDetailComponent implements OnInit {
    tipoDePago: ITipoDePago;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDePago }) => {
            this.tipoDePago = tipoDePago;
        });
    }

    previousState() {
        window.history.back();
    }
}
