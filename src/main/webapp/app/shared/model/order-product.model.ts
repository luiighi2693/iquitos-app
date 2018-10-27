import { IProduct } from 'app/shared/model//product.model';

export const enum OrderStatus {
    CREADO = 'CREADO',
    CANCELADO = 'CANCELADO',
    ENVIADO_EN_PROCESO = 'ENVIADO_EN_PROCESO',
    ENVIADO_CANCELADO = 'ENVIADO_CANCELADO',
    ENTREGADO = 'ENTREGADO',
    ENTREGADO_ERROR = 'ENTREGADO_ERROR'
}

export interface IOrderProduct {
    id?: number;
    note?: string;
    guide?: string;
    orderStatus?: OrderStatus;
    metaData?: string;
    providerId?: number;
    products?: IProduct[];
}

export class OrderProduct implements IOrderProduct {
    constructor(
        public id?: number,
        public note?: string,
        public guide?: string,
        public orderStatus?: OrderStatus,
        public metaData?: string,
        public providerId?: number,
        public products?: IProduct[]
    ) {}
}
