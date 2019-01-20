import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProductoDetalle } from 'app/shared/model/producto-detalle.model';
import { ProductoDetalleService } from './producto-detalle.service';
import { IVariante } from 'app/shared/model/variante.model';
import { VarianteService } from 'app/entities/variante';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
    selector: 'jhi-producto-detalle-update',
    templateUrl: './producto-detalle-update.component.html'
})
export class ProductoDetalleUpdateComponent implements OnInit {
    productoDetalle: IProductoDetalle;
    isSaving: boolean;

    variantes: IVariante[];

    productos: IProducto[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private productoDetalleService: ProductoDetalleService,
        private varianteService: VarianteService,
        private productoService: ProductoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productoDetalle }) => {
            this.productoDetalle = productoDetalle;
        });
        this.varianteService.query().subscribe(
            (res: HttpResponse<IVariante[]>) => {
                this.variantes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.productoDetalle.id !== undefined) {
            this.subscribeToSaveResponse(this.productoDetalleService.update(this.productoDetalle));
        } else {
            this.subscribeToSaveResponse(this.productoDetalleService.create(this.productoDetalle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProductoDetalle>>) {
        result.subscribe((res: HttpResponse<IProductoDetalle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVarianteById(index: number, item: IVariante) {
        return item.id;
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
