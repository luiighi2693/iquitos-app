import { Moment } from 'moment';
import { IAmortizacion } from './amortizacion.model';
import { IProducto } from './producto.model';
import { IProductoDetalle } from './producto-detalle.model';

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
    fecha?: Moment;
    estatus?: SellStatus;
    glosa?: string;
    metaData?: string;
    clienteNombre?: string;
    clienteId?: number;
    empleadoNombre?: string;
    empleadoId?: number;
    cajaId?: number;
    amortizacions?: IAmortizacion[];
    tipoDeDocumentoDeVentaNombre?: string;
    tipoDeDocumentoDeVentaId?: number;
    tipoDePagoNombre?: string;
    tipoDePagoId?: number;
    productos?: IProducto[];
    productoDetalles?: IProductoDetalle[];
}

export class Venta implements IVenta {
    constructor(
        public id?: number,
        public codigo?: string,
        public subTotal?: number,
        public impuesto?: number,
        public montoTotal?: number,
        public fecha?: Moment,
        public estatus?: SellStatus,
        public glosa?: string,
        public metaData?: string,
        public clienteNombre?: string,
        public clienteId?: number,
        public empleadoNombre?: string,
        public empleadoId?: number,
        public cajaId?: number,
        public amortizacions?: IAmortizacion[],
        public tipoDeDocumentoDeVentaNombre?: string,
        public tipoDeDocumentoDeVentaId?: number,
        public tipoDePagoNombre?: string,
        public tipoDePagoId?: number,
        public productos?: IProducto[],
        public productoDetalles?: IProductoDetalle[]
    ) {}
}
