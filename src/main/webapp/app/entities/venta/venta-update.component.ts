import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IVenta } from 'app/shared/model/venta.model';
import { VentaService } from './venta.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IEmpleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from 'app/entities/empleado';
import { ICaja } from 'app/shared/model/caja.model';
import { CajaService } from 'app/entities/caja';
import { ITipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';
import { TipoDeDocumentoDeVentaService } from 'app/entities/tipo-de-documento-de-venta';
import { ITipoDePago } from 'app/shared/model/tipo-de-pago.model';
import { TipoDePagoService } from 'app/entities/tipo-de-pago';
import { IEstatusDeProductoEntregado } from 'app/shared/model/estatus-de-producto-entregado.model';
import { EstatusDeProductoEntregadoService } from 'app/entities/estatus-de-producto-entregado';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
    selector: 'jhi-venta-update',
    templateUrl: './venta-update.component.html'
})
export class VentaUpdateComponent implements OnInit {
    venta: IVenta;
    isSaving: boolean;

    clientes: ICliente[];

    empleados: IEmpleado[];

    cajas: ICaja[];

    tipodedocumentodeventas: ITipoDeDocumentoDeVenta[];

    tipodepagos: ITipoDePago[];

    estatusdeproductoentregados: IEstatusDeProductoEntregado[];

    productos: IProducto[];
    fechaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private ventaService: VentaService,
        private clienteService: ClienteService,
        private empleadoService: EmpleadoService,
        private cajaService: CajaService,
        private tipoDeDocumentoDeVentaService: TipoDeDocumentoDeVentaService,
        private tipoDePagoService: TipoDePagoService,
        private estatusDeProductoEntregadoService: EstatusDeProductoEntregadoService,
        private productoService: ProductoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ venta }) => {
            this.venta = venta;
        });
        this.clienteService.query({ filter: 'venta-is-null' }).subscribe(
            (res: HttpResponse<ICliente[]>) => {
                if (!this.venta.clienteId) {
                    this.clientes = res.body;
                } else {
                    this.clienteService.find(this.venta.clienteId).subscribe(
                        (subRes: HttpResponse<ICliente>) => {
                            this.clientes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.empleadoService.query({ filter: 'venta-is-null' }).subscribe(
            (res: HttpResponse<IEmpleado[]>) => {
                if (!this.venta.empleadoId) {
                    this.empleados = res.body;
                } else {
                    this.empleadoService.find(this.venta.empleadoId).subscribe(
                        (subRes: HttpResponse<IEmpleado>) => {
                            this.empleados = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cajaService.query({ filter: 'venta-is-null' }).subscribe(
            (res: HttpResponse<ICaja[]>) => {
                if (!this.venta.cajaId) {
                    this.cajas = res.body;
                } else {
                    this.cajaService.find(this.venta.cajaId).subscribe(
                        (subRes: HttpResponse<ICaja>) => {
                            this.cajas = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoDeDocumentoDeVentaService.query().subscribe(
            (res: HttpResponse<ITipoDeDocumentoDeVenta[]>) => {
                this.tipodedocumentodeventas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoDePagoService.query().subscribe(
            (res: HttpResponse<ITipoDePago[]>) => {
                this.tipodepagos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.estatusDeProductoEntregadoService.query().subscribe(
            (res: HttpResponse<IEstatusDeProductoEntregado[]>) => {
                this.estatusdeproductoentregados = res.body;
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
        if (this.venta.id !== undefined) {
            this.subscribeToSaveResponse(this.ventaService.update(this.venta));
        } else {
            this.subscribeToSaveResponse(this.ventaService.create(this.venta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>) {
        result.subscribe((res: HttpResponse<IVenta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }

    trackEmpleadoById(index: number, item: IEmpleado) {
        return item.id;
    }

    trackCajaById(index: number, item: ICaja) {
        return item.id;
    }

    trackTipoDeDocumentoDeVentaById(index: number, item: ITipoDeDocumentoDeVenta) {
        return item.id;
    }

    trackTipoDePagoById(index: number, item: ITipoDePago) {
        return item.id;
    }

    trackEstatusDeProductoEntregadoById(index: number, item: IEstatusDeProductoEntregado) {
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
