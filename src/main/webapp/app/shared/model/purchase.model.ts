import { Moment } from 'moment';
import { IProduct } from 'app/shared/model//product.model';

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
    metaData?: string;
    providerId?: number;
    documentTypePurchaseId?: number;
    purchaseStatusId?: number;
    boxId?: number;
    products?: IProduct[];
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
        public metaData?: string,
        public providerId?: number,
        public documentTypePurchaseId?: number,
        public purchaseStatusId?: number,
        public boxId?: number,
        public products?: IProduct[]
    ) {}
}
