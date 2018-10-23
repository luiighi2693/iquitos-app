import { Moment } from 'moment';

export interface IAmortization {
    id?: number;
    amountToPay?: number;
    amountPayed?: number;
    emissionDate?: Moment;
    documentCode?: string;
    gloss?: string;
    documentTypeSellId?: number;
    paymentTypeId?: number;
    sellId?: number;
}

export class Amortization implements IAmortization {
    constructor(
        public id?: number,
        public amountToPay?: number,
        public amountPayed?: number,
        public emissionDate?: Moment,
        public documentCode?: string,
        public gloss?: string,
        public documentTypeSellId?: number,
        public paymentTypeId?: number,
        public sellId?: number
    ) {}
}
