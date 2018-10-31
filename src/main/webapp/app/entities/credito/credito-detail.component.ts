import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICredito } from 'app/shared/model/credito.model';

@Component({
    selector: 'jhi-credito-detail',
    templateUrl: './credito-detail.component.html'
})
export class CreditoDetailComponent implements OnInit {
    credito: ICredito;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ credito }) => {
            this.credito = credito;
        });
    }

    previousState() {
        window.history.back();
    }
}
