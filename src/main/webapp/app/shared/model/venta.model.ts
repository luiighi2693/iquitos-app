import { Moment } from 'moment';
import { IProducto } from 'app/shared/model//producto.model';
import { IProductoDetalle } from 'app/shared/model//producto-detalle.model';
import { IAmortizacion } from 'app/shared/model//amortizacion.model';

export const enum SellStatus {
    PENDIENTE = 'PENDIENTE',
    COMPLETADO = 'COMPLETADO'
}

export interface IVenta {
    id?: number;
    codigo?: string;
    subTotal?: number;
    impuesto?: number;
    montoTotal?: number;
    diasCredito?: number;
    fecha?: Moment;
    estatus?: SellStatus;
    glosa?: string;
    metaData?: string;
    cajaId?: number;
    tipoDeDocumentoDeVentaNombre?: string;
    tipoDeDocumentoDeVentaId?: number;
    tipoDePagoNombre?: string;
    tipoDePagoId?: number;
    clienteNombre?: string;
    clienteId?: number;
    empleadoNombre?: string;
    empleadoId?: number;
    productos?: IProducto[];
    productoDetalles?: IProductoDetalle[];
    amortizacions?: IAmortizacion[];
}

export class Venta implements IVenta {
    constructor(
        public id?: number,
        public codigo?: string,
        public subTotal?: number,
        public impuesto?: number,
        public montoTotal?: number,
        public diasCredito?: number,
        public fecha?: Moment,
        public estatus?: SellStatus,
        public glosa?: string,
        public metaData?: string,
        public cajaId?: number,
        public tipoDeDocumentoDeVentaNombre?: string,
        public tipoDeDocumentoDeVentaId?: number,
        public tipoDePagoNombre?: string,
        public tipoDePagoId?: number,
        public clienteNombre?: string,
        public clienteId?: number,
        public empleadoNombre?: string,
        public empleadoId?: number,
        public productos?: IProducto[],
        public productoDetalles?: IProductoDetalle[],
        public amortizacions?: IAmortizacion[]
    ) {}
}
