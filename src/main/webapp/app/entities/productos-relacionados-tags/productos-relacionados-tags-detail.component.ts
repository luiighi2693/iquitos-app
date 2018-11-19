import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductosRelacionadosTags } from 'app/shared/model/productos-relacionados-tags.model';

@Component({
    selector: 'jhi-productos-relacionados-tags-detail',
    templateUrl: './productos-relacionados-tags-detail.component.html'
})
export class ProductosRelacionadosTagsDetailComponent implements OnInit {
    productosRelacionadosTags: IProductosRelacionadosTags;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productosRelacionadosTags }) => {
            this.productosRelacionadosTags = productosRelacionadosTags;
        });
    }

    previousState() {
        window.history.back();
    }
}
