import { Moment } from 'moment';

export const enum ProviderStatus {
    ACTIVO = 'ACTIVO',
    INACTIVO = 'INACTIVO'
}

export interface ICuentaProveedor {
    id?: number;
    codigo?: string;
    estatus?: ProviderStatus;
    banco?: string;
    nombreCuenta?: string;
    numeroDeCuenta?: number;
    fecha?: Moment;
}

export class CuentaProveedor implements ICuentaProveedor {
    constructor(
        public id?: number,
        public codigo?: string,
        public estatus?: ProviderStatus,
        public banco?: string,
        public nombreCuenta?: string,
        public numeroDeCuenta?: number,
        public fecha?: Moment
    ) {}
}
