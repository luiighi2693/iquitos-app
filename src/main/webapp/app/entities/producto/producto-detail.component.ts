import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProducto } from 'app/shared/model/producto.model';

@Component({
    selector: 'jhi-producto-detail',
    templateUrl: './producto-detail.component.html'
})
export class ProductoDetailComponent implements OnInit {
    producto: IProducto;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ producto }) => {
            this.producto = producto;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
