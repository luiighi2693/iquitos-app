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

export interface IClient {
    id?: number;
    name?: string;
    code?: string;
    address?: string;
    email?: string;
    cellphone?: string;
    birthday?: Moment;
    sex?: Sex;
    civilStatus?: CivilStatus;
    imageContentType?: string;
    image?: any;
    clientType?: ClientType;
    userId?: number;
    documentTypeId?: number;
}

export class Client implements IClient {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public address?: string,
        public email?: string,
        public cellphone?: string,
        public birthday?: Moment,
        public sex?: Sex,
        public civilStatus?: CivilStatus,
        public imageContentType?: string,
        public image?: any,
        public clientType?: ClientType,
        public userId?: number,
        public documentTypeId?: number
    ) {}
}
