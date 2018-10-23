import { Moment } from 'moment';

export interface IProviderPayment {
    id?: number;
    amountToPay?: number;
    amountPayed?: number;
    emissionDate?: Moment;
    documentCode?: string;
    glosa?: string;
    imageContentType?: string;
    image?: any;
    documentTypePurchaseId?: number;
    paymentTypeId?: number;
}

export class ProviderPayment implements IProviderPayment {
    constructor(
        public id?: number,
        public amountToPay?: number,
        public amountPayed?: number,
        public emissionDate?: Moment,
        public documentCode?: string,
        public glosa?: string,
        public imageContentType?: string,
        public image?: any,
        public documentTypePurchaseId?: number,
        public paymentTypeId?: number
    ) {}
}
