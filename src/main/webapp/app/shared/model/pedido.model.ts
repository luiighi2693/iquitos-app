import { IProducto } from 'app/shared/model//producto.model';

export const enum OrderStatus {
    CREADO = 'CREADO',
    CANCELADO = 'CANCELADO',
    ENVIADO_EN_PROCESO = 'ENVIADO_EN_PROCESO',
    ENVIADO_CANCELADO = 'ENVIADO_CANCELADO',
    ENTREGADO = 'ENTREGADO',
    ENTREGADO_ERROR = 'ENTREGADO_ERROR'
}

export interface IPedido {
    id?: number;
    nota?: string;
    guia?: string;
    estatus?: OrderStatus;
    metaData?: string;
    proveedorRazonSocial?: string;
    proveedorId?: number;
    productos?: IProducto[];
}

export class Pedido implements IPedido {
    constructor(
        public id?: number,
        public nota?: string,
        public guia?: string,
        public estatus?: OrderStatus,
        public metaData?: string,
        public proveedorRazonSocial?: string,
        public proveedorId?: number,
        public productos?: IProducto[]
    ) {}
}
