import { Moment } from 'moment';

export interface ICredit {
    id?: number;
    amount?: number;
    emissionDate?: Moment;
    paymentMode?: number;
    creditNumber?: number;
    totalAmount?: number;
    limitDate?: Moment;
    creditNote?: string;
    sellId?: number;
    purchaseId?: number;
}

export class Credit implements ICredit {
    constructor(
        public id?: number,
        public amount?: number,
        public emissionDate?: Moment,
        public paymentMode?: number,
        public creditNumber?: number,
        public totalAmount?: number,
        public limitDate?: Moment,
        public creditNote?: string,
        public sellId?: number,
        public purchaseId?: number
    ) {}
}
