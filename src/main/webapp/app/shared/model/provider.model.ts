import { IProviderAccount } from 'app/shared/model//provider-account.model';

export interface IProvider {
    id?: number;
    code?: string;
    socialReason?: string;
    address?: string;
    email?: string;
    cellphone?: number;
    providerAccounts?: IProviderAccount[];
}

export class Provider implements IProvider {
    constructor(
        public id?: number,
        public code?: string,
        public socialReason?: string,
        public address?: string,
        public email?: string,
        public cellphone?: number,
        public providerAccounts?: IProviderAccount[]
    ) {}
}
