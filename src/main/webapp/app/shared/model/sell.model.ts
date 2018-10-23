import { Moment } from 'moment';
import { IAmortization } from 'app/shared/model//amortization.model';

export const enum SellStatus {
    PENDIENTE = 'PENDIENTE',
    COMPLETADO = 'COMPLETADO'
}

export interface ISell {
    id?: number;
    code?: string;
    subTotalAmount?: number;
    taxAmount?: number;
    totalAmount?: number;
    date?: Moment;
    status?: SellStatus;
    gloss?: string;
    clientId?: number;
    employeeId?: number;
    boxId?: number;
    amortizations?: IAmortization[];
    documentTypeSellId?: number;
    paymentTypeId?: number;
    productsDeliveredStatusId?: number;
}

export class Sell implements ISell {
    constructor(
        public id?: number,
        public code?: string,
        public subTotalAmount?: number,
        public taxAmount?: number,
        public totalAmount?: number,
        public date?: Moment,
        public status?: SellStatus,
        public gloss?: string,
        public clientId?: number,
        public employeeId?: number,
        public boxId?: number,
        public amortizations?: IAmortization[],
        public documentTypeSellId?: number,
        public paymentTypeId?: number,
        public productsDeliveredStatusId?: number
    ) {}
}
