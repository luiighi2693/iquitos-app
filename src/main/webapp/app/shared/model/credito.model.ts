import { Moment } from 'moment';

export interface ICredito {
    id?: number;
    monto?: number;
    fecha?: Moment;
    modoDePago?: number;
    numero?: number;
    montoTotal?: number;
    fechaLimite?: Moment;
    notaDeCredito?: string;
    ventaId?: number;
    compraId?: number;
}

export class Credito implements ICredito {
    constructor(
        public id?: number,
        public monto?: number,
        public fecha?: Moment,
        public modoDePago?: number,
        public numero?: number,
        public montoTotal?: number,
        public fechaLimite?: Moment,
        public notaDeCredito?: string,
        public ventaId?: number,
        public compraId?: number
    ) {}
}
