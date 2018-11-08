import { Moment } from 'moment';

export const enum Sex {
    MASCULINO = 'MASCULINO',
    FEMENINO = 'FEMENINO'
}

export const enum CivilStatus {
    SOLTERO = 'SOLTERO',
    CASADO = 'CASADO'
}

export const enum ClientType {
    NATURAL = 'NATURAL',
    JURIDICO = 'JURIDICO'
}

export interface ICliente {
    id?: number;
    nombre?: string;
    codigo?: string;
    direccion?: string;
    correo?: string;
    telefono?: string;
    fechaDeNacimiento?: Moment;
    sexo?: Sex;
    estatusCivil?: CivilStatus;
    imagenContentType?: string;
    imagen?: any;
    tipoDeCliente?: ClientType;
    usuarioDni?: string;
    usuarioId?: number;
    tipoDeDocumentoNombre?: string;
    tipoDeDocumentoId?: number;
}

export class Cliente implements ICliente {
    constructor(
        public id?: number,
        public nombre?: string,
        public codigo?: string,
        public direccion?: string,
        public correo?: string,
        public telefono?: string,
        public fechaDeNacimiento?: Moment,
        public sexo?: Sex,
        public estatusCivil?: CivilStatus,
        public imagenContentType?: string,
        public imagen?: any,
        public tipoDeCliente?: ClientType,
        public usuarioDni?: string,
        public usuarioId?: number,
        public tipoDeDocumentoNombre?: string,
        public tipoDeDocumentoId?: number
    ) {}
}
