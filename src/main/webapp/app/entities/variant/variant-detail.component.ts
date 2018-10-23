import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVariant } from 'app/shared/model/variant.model';

@Component({
    selector: 'jhi-variant-detail',
    templateUrl: './variant-detail.component.html'
})
export class VariantDetailComponent implements OnInit {
    variant: IVariant;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ variant }) => {
            this.variant = variant;
        });
    }

    previousState() {
        window.history.back();
    }
}
