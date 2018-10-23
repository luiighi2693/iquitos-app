import { Moment } from 'moment';
import { IProduct } from 'app/shared/model//product.model';

export interface IPurchaseHasProduct {
    id?: number;
    quantity?: number;
    tax?: number;
    datePurchase?: Moment;
    purchaseId?: number;
    products?: IProduct[];
}

export class PurchaseHasProduct implements IPurchaseHasProduct {
    constructor(
        public id?: number,
        public quantity?: number,
        public tax?: number,
        public datePurchase?: Moment,
        public purchaseId?: number,
        public products?: IProduct[]
    ) {}
}
