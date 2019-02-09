import { Moment } from 'moment';

export interface IAmortizacion {
    id?: number;
    codigo?: string;
    monto?: number;
    montoPagado?: number;
    fecha?: Moment;
    codigoDocumento?: string;
    glosa?: string;
    comprobante?: string;
    fotoComprobanteContentType?: string;
    fotoComprobante?: any;
    tipoDeDocumentoDeVentaNombre?: string;
    tipoDeDocumentoDeVentaId?: number;
    tipoDePagoNombre?: string;
    tipoDePagoId?: number;
}

export class Amortizacion implements IAmortizacion {
    constructor(
        public id?: number,
        public codigo?: string,
        public monto?: number,
        public montoPagado?: number,
        public fecha?: Moment,
        public codigoDocumento?: string,
        public glosa?: string,
        public comprobante?: string,
        public fotoComprobanteContentType?: string,
        public fotoComprobante?: any,
        public tipoDeDocumentoDeVentaNombre?: string,
        public tipoDeDocumentoDeVentaId?: number,
        public tipoDePagoNombre?: string,
        public tipoDePagoId?: number
    ) {}
}
