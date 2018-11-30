import { Moment } from 'moment';
import { IVariante } from './variante.model';

export const enum ProductType {
    BIENES = 'BIENES',
    SERVICIOS = 'SERVICIOS'
}

export interface IProducto {
    id?: number;
    codigo?: string;
    descripcion?: string;
    fechaExpiracion?: Moment;
    esFavorito?: boolean;
    visibleParaLaVenta?: boolean;
    imagenContentType?: string;
    imagen?: any;
    stock?: number;
    notificacionDeLimiteDeStock?: number;
    tipoDeProducto?: ProductType;
    unidadDeMedidaNombre?: string;
    unidadDeMedidaId?: number;
    categoriaNombre?: string;
    categoriaId?: number;
    variantes?: IVariante[];
}

export class Producto implements IProducto {
    constructor(
        public id?: number,
        public codigo?: string,
        public descripcion?: string,
        public fechaExpiracion?: Moment,
        public esFavorito?: boolean,
        public visibleParaLaVenta?: boolean,
        public imagenContentType?: string,
        public imagen?: any,
        public stock?: number,
        public notificacionDeLimiteDeStock?: number,
        public tipoDeProducto?: ProductType,
        public unidadDeMedidaNombre?: string,
        public unidadDeMedidaId?: number,
        public categoriaNombre?: string,
        public categoriaId?: number,
        public variantes?: IVariante[]
    ) {
        this.esFavorito = this.esFavorito || false;
        this.visibleParaLaVenta = this.visibleParaLaVenta || false;
    }
}