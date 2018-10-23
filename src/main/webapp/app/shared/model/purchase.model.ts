import { Moment } from 'moment';

export const enum PurchaseLocation {
    TIENDA = 'TIENDA'
}

export const enum PaymentPurchaseType {
    CONTADO = 'CONTADO',
    CREDITO = 'CREDITO'
}

export interface IPurchase {
    id?: number;
    date?: Moment;
    remisionGuide?: string;
    documentNumber?: string;
    location?: PurchaseLocation;
    totalAmount?: number;
    correlative?: string;
    paymentPurchaseType?: PaymentPurchaseType;
    providerId?: number;
    documentTypePurchaseId?: number;
    purchaseStatusId?: number;
    boxId?: number;
}

export class Purchase implements IPurchase {
    constructor(
        public id?: number,
        public date?: Moment,
        public remisionGuide?: string,
        public documentNumber?: string,
        public location?: PurchaseLocation,
        public totalAmount?: number,
        public correlative?: string,
        public paymentPurchaseType?: PaymentPurchaseType,
        public providerId?: number,
        public documentTypePurchaseId?: number,
        public purchaseStatusId?: number,
        public boxId?: number
    ) {}
}
