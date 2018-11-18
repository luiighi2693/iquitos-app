import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
    selector: 'jhi-pedido-update',
    templateUrl: './pedido-update.component.html'
})
export class PedidoUpdateComponent implements OnInit {
    pedido: IPedido;
    isSaving: boolean;

    proveedors: IProveedor[];

    productos: IProducto[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private pedidoService: PedidoService,
        private proveedorService: ProveedorService,
        private productoService: ProductoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pedido }) => {
            this.pedido = pedido;
        });
        this.proveedorService.query({ filter: 'pedido-is-null' }).subscribe(
            (res: HttpResponse<IProveedor[]>) => {
                if (!this.pedido.proveedorId) {
                    this.proveedors = res.body;
                } else {
                    this.proveedorService.find(this.pedido.proveedorId).subscribe(
                        (subRes: HttpResponse<IProveedor>) => {
                            this.proveedors = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
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
        if (this.pedido.id !== undefined) {
            this.subscribeToSaveResponse(this.pedidoService.update(this.pedido));
        } else {
            this.subscribeToSaveResponse(this.pedidoService.create(this.pedido));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>) {
        result.subscribe((res: HttpResponse<IPedido>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProveedorById(index: number, item: IProveedor) {
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
