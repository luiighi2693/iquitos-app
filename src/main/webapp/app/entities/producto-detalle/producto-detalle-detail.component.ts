import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductoDetalle } from 'app/shared/model/producto-detalle.model';

@Component({
    selector: 'jhi-producto-detalle-detail',
    templateUrl: './producto-detalle-detail.component.html'
})
export class ProductoDetalleDetailComponent implements OnInit {
    productoDetalle: IProductoDetalle;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productoDetalle }) => {
            this.productoDetalle = productoDetalle;
        });
    }

    previousState() {
        window.history.back();
    }
}
