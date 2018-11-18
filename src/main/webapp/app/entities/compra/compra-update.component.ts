import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ICompra } from 'app/shared/model/compra.model';
import { CompraService } from './compra.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { ITipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';
import { TipoDeDocumentoDeCompraService } from 'app/entities/tipo-de-documento-de-compra';
import { IEstatusDeCompra } from 'app/shared/model/estatus-de-compra.model';
import { EstatusDeCompraService } from 'app/entities/estatus-de-compra';
import { ICaja } from 'app/shared/model/caja.model';
import { CajaService } from 'app/entities/caja';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
    selector: 'jhi-compra-update',
    templateUrl: './compra-update.component.html'
})
export class CompraUpdateComponent implements OnInit {
    compra: ICompra;
    isSaving: boolean;

    proveedors: IProveedor[];

    tipodedocumentodecompras: ITipoDeDocumentoDeCompra[];

    estatusdecompras: IEstatusDeCompra[];

    cajas: ICaja[];

    productos: IProducto[];
    fechaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private compraService: CompraService,
        private proveedorService: ProveedorService,
        private tipoDeDocumentoDeCompraService: TipoDeDocumentoDeCompraService,
        private estatusDeCompraService: EstatusDeCompraService,
        private cajaService: CajaService,
        private productoService: ProductoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compra }) => {
            this.compra = compra;
        });
        this.proveedorService.query({ filter: 'compra-is-null' }).subscribe(
            (res: HttpResponse<IProveedor[]>) => {
                if (!this.compra.proveedorId) {
                    this.proveedors = res.body;
                } else {
                    this.proveedorService.find(this.compra.proveedorId).subscribe(
                        (subRes: HttpResponse<IProveedor>) => {
                            this.proveedors = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoDeDocumentoDeCompraService.query({ filter: 'compra-is-null' }).subscribe(
            (res: HttpResponse<ITipoDeDocumentoDeCompra[]>) => {
                if (!this.compra.tipoDeDocumentoDeCompraId) {
                    this.tipodedocumentodecompras = res.body;
                } else {
                    this.tipoDeDocumentoDeCompraService.find(this.compra.tipoDeDocumentoDeCompraId).subscribe(
                        (subRes: HttpResponse<ITipoDeDocumentoDeCompra>) => {
                            this.tipodedocumentodecompras = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.estatusDeCompraService.query({ filter: 'compra-is-null' }).subscribe(
            (res: HttpResponse<IEstatusDeCompra[]>) => {
                if (!this.compra.estatusDeCompraId) {
                    this.estatusdecompras = res.body;
                } else {
                    this.estatusDeCompraService.find(this.compra.estatusDeCompraId).subscribe(
                        (subRes: HttpResponse<IEstatusDeCompra>) => {
                            this.estatusdecompras = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cajaService.query({ filter: 'compra-is-null' }).subscribe(
            (res: HttpResponse<ICaja[]>) => {
                if (!this.compra.cajaId) {
                    this.cajas = res.body;
                } else {
                    this.cajaService.find(this.compra.cajaId).subscribe(
                        (subRes: HttpResponse<ICaja>) => {
                            this.cajas = [subRes.body].concat(res.body);
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
        if (this.compra.id !== undefined) {
            this.subscribeToSaveResponse(this.compraService.update(this.compra));
        } else {
            this.subscribeToSaveResponse(this.compraService.create(this.compra));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICompra>>) {
        result.subscribe((res: HttpResponse<ICompra>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTipoDeDocumentoDeCompraById(index: number, item: ITipoDeDocumentoDeCompra) {
        return item.id;
    }

    trackEstatusDeCompraById(index: number, item: IEstatusDeCompra) {
        return item.id;
    }

    trackCajaById(index: number, item: ICaja) {
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
