import { Moment } from 'moment';

export interface ICaja {
    id?: number;
    monto?: number;
    montoActual?: number;
    fechaInicial?: Moment;
    fechaFinal?: Moment;
    cajaId?: number;
}

export class Caja implements ICaja {
    constructor(
        public id?: number,
        public monto?: number,
        public montoActual?: number,
        public fechaInicial?: Moment,
        public fechaFinal?: Moment,
        public cajaId?: number
    ) {}
}
