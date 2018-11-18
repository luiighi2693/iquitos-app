import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVariante } from 'app/shared/model/variante.model';
import { VarianteService } from './variante.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
    selector: 'jhi-variante-update',
    templateUrl: './variante-update.component.html'
})
export class VarianteUpdateComponent implements OnInit {
    variante: IVariante;
    isSaving: boolean;

    productos: IProducto[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private varianteService: VarianteService,
        private productoService: ProductoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ variante }) => {
            this.variante = variante;
        });
        this.productoService.query().subscribe(
            (res: HttpResponse<IProducto[]>) => {
                this.productos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.variante.id !== undefined) {
            this.subscribeToSaveResponse(this.varianteService.update(this.variante));
        } else {
            this.subscribeToSaveResponse(this.varianteService.create(this.variante));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVariante>>) {
        result.subscribe((res: HttpResponse<IVariante>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductoById(index: number, item: IProducto) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
