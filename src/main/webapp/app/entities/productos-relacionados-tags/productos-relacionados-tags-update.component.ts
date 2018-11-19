import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IProductosRelacionadosTags } from 'app/shared/model/productos-relacionados-tags.model';
import { ProductosRelacionadosTagsService } from './productos-relacionados-tags.service';

@Component({
    selector: 'jhi-productos-relacionados-tags-update',
    templateUrl: './productos-relacionados-tags-update.component.html'
})
export class ProductosRelacionadosTagsUpdateComponent implements OnInit {
    productosRelacionadosTags: IProductosRelacionadosTags;
    isSaving: boolean;

    constructor(private productosRelacionadosTagsService: ProductosRelacionadosTagsService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productosRelacionadosTags }) => {
            this.productosRelacionadosTags = productosRelacionadosTags;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productosRelacionadosTags.id !== undefined) {
            this.subscribeToSaveResponse(this.productosRelacionadosTagsService.update(this.productosRelacionadosTags));
        } else {
            this.subscribeToSaveResponse(this.productosRelacionadosTagsService.create(this.productosRelacionadosTags));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProductosRelacionadosTags>>) {
        result.subscribe(
            (res: HttpResponse<IProductosRelacionadosTags>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
