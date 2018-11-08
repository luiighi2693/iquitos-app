import { Moment } from 'moment';

export interface IPagoDeProveedor {
    id?: number;
    monto?: number;
    montoPagado?: number;
    fecha?: Moment;
    codigoDeDocumento?: string;
    glosa?: string;
    imagenContentType?: string;
    imagen?: any;
    tipoDeDocumentoDeCompraNombre?: string;
    tipoDeDocumentoDeCompraId?: number;
    tipoDePagoNombre?: string;
    tipoDePagoId?: number;
}

export class PagoDeProveedor implements IPagoDeProveedor {
    constructor(
        public id?: number,
        public monto?: number,
        public montoPagado?: number,
        public fecha?: Moment,
        public codigoDeDocumento?: string,
        public glosa?: string,
        public imagenContentType?: string,
        public imagen?: any,
        public tipoDeDocumentoDeCompraNombre?: string,
        public tipoDeDocumentoDeCompraId?: number,
        public tipoDePagoNombre?: string,
        public tipoDePagoId?: number
    ) {}
}
