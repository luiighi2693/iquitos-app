import { Moment } from 'moment';

export interface IAmortizacion {
    id?: number;
    monto?: number;
    montoPagado?: number;
    fecha?: Moment;
    codigoDocumento?: string;
    glosa?: string;
    tipoDeDocumentoDeVentaNombre?: string;
    tipoDeDocumentoDeVentaId?: number;
    tipoDePagoNombre?: string;
    tipoDePagoId?: number;
}

export class Amortizacion implements IAmortizacion {
    constructor(
        public id?: number,
        public monto?: number,
        public montoPagado?: number,
        public fecha?: Moment,
        public codigoDocumento?: string,
        public glosa?: string,
        public tipoDeDocumentoDeVentaNombre?: string,
        public tipoDeDocumentoDeVentaId?: number,
        public tipoDePagoNombre?: string,
        public tipoDePagoId?: number
    ) {}
}
