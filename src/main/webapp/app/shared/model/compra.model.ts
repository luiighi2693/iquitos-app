import { Moment } from 'moment';
import { IProducto } from 'app/shared/model//producto.model';

export const enum PurchaseLocation {
    TIENDA = 'TIENDA'
}

export const enum PaymentPurchaseType {
    CONTADO = 'CONTADO',
    CREDITO = 'CREDITO'
}

export interface ICompra {
    id?: number;
    fecha?: Moment;
    guiaRemision?: string;
    numeroDeDocumento?: string;
    ubicacion?: PurchaseLocation;
    montoTotal?: number;
    correlativo?: string;
    tipoDePagoDeCompra?: PaymentPurchaseType;
    metaData?: string;
    proveedorId?: number;
    tipoDeDocumentoDeCompraNombre?: string;
    tipoDeDocumentoDeCompraId?: number;
    estatusDeCompraNombre?: string;
    estatusDeCompraId?: number;
    cajaId?: number;
    productos?: IProducto[];
}

export class Compra implements ICompra {
    constructor(
        public id?: number,
        public fecha?: Moment,
        public guiaRemision?: string,
        public numeroDeDocumento?: string,
        public ubicacion?: PurchaseLocation,
        public montoTotal?: number,
        public correlativo?: string,
        public tipoDePagoDeCompra?: PaymentPurchaseType,
        public metaData?: string,
        public proveedorId?: number,
        public tipoDeDocumentoDeCompraNombre?: string,
        public tipoDeDocumentoDeCompraId?: number,
        public estatusDeCompraNombre?: string,
        public estatusDeCompraId?: number,
        public cajaId?: number,
        public productos?: IProducto[]
    ) {}
}
