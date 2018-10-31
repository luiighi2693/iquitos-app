import { Moment } from 'moment';

export const enum OperationType {
    INGRESO = 'INGRESO',
    EGRESO = 'EGRESO'
}

export interface IOperacion {
    id?: number;
    fecha?: Moment;
    glosa?: string;
    monto?: number;
    tipoDeOperacion?: OperationType;
    cajaId?: number;
    tipoDePagoNombre?: string;
    tipoDePagoId?: number;
    tipoDeOperacionDeIngresoNombre?: string;
    tipoDeOperacionDeIngresoId?: number;
    tipoDeOperacionDeGastoNombre?: string;
    tipoDeOperacionDeGastoId?: number;
}

export class Operacion implements IOperacion {
    constructor(
        public id?: number,
        public fecha?: Moment,
        public glosa?: string,
        public monto?: number,
        public tipoDeOperacion?: OperationType,
        public cajaId?: number,
        public tipoDePagoNombre?: string,
        public tipoDePagoId?: number,
        public tipoDeOperacionDeIngresoNombre?: string,
        public tipoDeOperacionDeIngresoId?: number,
        public tipoDeOperacionDeGastoNombre?: string,
        public tipoDeOperacionDeGastoId?: number
    ) {}
}
