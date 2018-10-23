import { IProduct } from 'app/shared/model//product.model';

export interface ISellHasProduct {
    id?: number;
    quantity?: number;
    discount?: number;
    sellId?: number;
    variantId?: number;
    products?: IProduct[];
}

export class SellHasProduct implements ISellHasProduct {
    constructor(
        public id?: number,
        public quantity?: number,
        public discount?: number,
        public sellId?: number,
        public variantId?: number,
        public products?: IProduct[]
    ) {}
}
