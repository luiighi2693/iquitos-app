import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IVenta } from 'app/shared/model/venta.model';
import { VentaService } from './venta.service';
import { ICaja } from 'app/shared/model/caja.model';
import { CajaService } from 'app/entities/caja';
import { ITipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';
import { TipoDeDocumentoDeVentaService } from 'app/entities/tipo-de-documento-de-venta';
import { ITipoDePago } from 'app/shared/model/tipo-de-pago.model';
import { TipoDePagoService } from 'app/entities/tipo-de-pago';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IEmpleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from 'app/entities/empleado';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IProductoDetalle } from 'app/shared/model/producto-detalle.model';
import { ProductoDetalleService } from 'app/entities/producto-detalle';
import { IAmortizacion } from 'app/shared/model/amortizacion.model';
import { AmortizacionService } from 'app/entities/amortizacion';

@Component({
    selector: 'jhi-venta-update',
    templateUrl: './venta-update.component.html'
})
export class VentaUpdateComponent implements OnInit {
    venta: IVenta;
    isSaving: boolean;

    cajas: ICaja[];

    tipodedocumentodeventas: ITipoDeDocumentoDeVenta[];

    tipodepagos: ITipoDePago[];

    clientes: ICliente[];

    empleados: IEmpleado[];

    productos: IProducto[];

    productodetalles: IProductoDetalle[];

    amortizacions: IAmortizacion[];
    fechaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private ventaService: VentaService,
        private cajaService: CajaService,
        private tipoDeDocumentoDeVentaService: TipoDeDocumentoDeVentaService,
        private tipoDePagoService: TipoDePagoService,
        private clienteService: ClienteService,
        private empleadoService: EmpleadoService,
        private productoService: ProductoService,
        private productoDetalleService: ProductoDetalleService,
        private amortizacionService: AmortizacionService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ venta }) => {
            this.venta = venta;
        });
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
        this.clienteService.query().subscribe(
            (res: HttpResponse<ICliente[]>) => {
                this.clientes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.empleadoService.query().subscribe(
            (res: HttpResponse<IEmpleado[]>) => {
                this.empleados = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.productoService.query().subscribe(
            (res: HttpResponse<IProducto[]>) => {
                this.productos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.productoDetalleService.query().subscribe(
            (res: HttpResponse<IProductoDetalle[]>) => {
                this.productodetalles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.amortizacionService.query().subscribe(
            (res: HttpResponse<IAmortizacion[]>) => {
                this.amortizacions = res.body;
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

    trackCajaById(index: number, item: ICaja) {
        return item.id;
    }

    trackTipoDeDocumentoDeVentaById(index: number, item: ITipoDeDocumentoDeVenta) {
        return item.id;
    }

    trackTipoDePagoById(index: number, item: ITipoDePago) {
        return item.id;
    }

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }

    trackEmpleadoById(index: number, item: IEmpleado) {
        return item.id;
    }

    trackProductoById(index: number, item: IProducto) {
        return item.id;
    }

    trackProductoDetalleById(index: number, item: IProductoDetalle) {
        return item.id;
    }

    trackAmortizacionById(index: number, item: IAmortizacion) {
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
