import { Moment } from 'moment';

export const enum ProviderStatus {
    ACTIVO = 'ACTIVO',
    INACTIVO = 'INACTIVO'
}

export interface IProviderAccount {
    id?: number;
    code?: string;
    status?: ProviderStatus;
    bank?: string;
    accountName?: string;
    accountNumber?: number;
    initDate?: Moment;
    providerId?: number;
}

export class ProviderAccount implements IProviderAccount {
    constructor(
        public id?: number,
        public code?: string,
        public status?: ProviderStatus,
        public bank?: string,
        public accountName?: string,
        public accountNumber?: number,
        public initDate?: Moment,
        public providerId?: number
    ) {}
}
