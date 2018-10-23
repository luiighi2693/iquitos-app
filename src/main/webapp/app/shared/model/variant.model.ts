import { IProduct } from 'app/shared/model//product.model';

export interface IVariant {
    id?: number;
    name?: string;
    description?: string;
    priceSell?: number;
    privePurchase?: number;
    products?: IProduct[];
}

export class Variant implements IVariant {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public priceSell?: number,
        public privePurchase?: number,
        public products?: IProduct[]
    ) {}
}
