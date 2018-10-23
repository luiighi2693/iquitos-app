export interface IProductsDeliveredStatus {
    id?: number;
    value?: string;
    metaData?: string;
}

export class ProductsDeliveredStatus implements IProductsDeliveredStatus {
    constructor(public id?: number, public value?: string, public metaData?: string) {}
}
