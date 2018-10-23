import { Moment } from 'moment';

export const enum ProductType {
    BIENES = 'BIENES',
    SERVICIOS = 'SERVICIOS'
}

export interface IProduct {
    id?: number;
    code?: string;
    description?: string;
    dni?: number;
    expirationDate?: Moment;
    isFavorite?: boolean;
    visibleToSell?: boolean;
    imageContentType?: string;
    image?: any;
    stock?: number;
    stockLimitNotification?: number;
    productType?: ProductType;
    unitMeasurementId?: number;
    categoryId?: number;
    vartiantId?: number;
    variantId?: number;
    sellHasProductId?: number;
    purchaseHasProductId?: number;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public code?: string,
        public description?: string,
        public dni?: number,
        public expirationDate?: Moment,
        public isFavorite?: boolean,
        public visibleToSell?: boolean,
        public imageContentType?: string,
        public image?: any,
        public stock?: number,
        public stockLimitNotification?: number,
        public productType?: ProductType,
        public unitMeasurementId?: number,
        public categoryId?: number,
        public vartiantId?: number,
        public variantId?: number,
        public sellHasProductId?: number,
        public purchaseHasProductId?: number
    ) {
        this.isFavorite = this.isFavorite || false;
        this.visibleToSell = this.visibleToSell || false;
    }
}
