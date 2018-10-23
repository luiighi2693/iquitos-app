import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISell } from 'app/shared/model/sell.model';

@Component({
    selector: 'jhi-sell-detail',
    templateUrl: './sell-detail.component.html'
})
export class SellDetailComponent implements OnInit {
    sell: ISell;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sell }) => {
            this.sell = sell;
        });
    }

    previousState() {
        window.history.back();
    }
}
