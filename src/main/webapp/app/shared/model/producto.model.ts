import { IVariante } from 'app/shared/model//variante.model';

export const enum UnidadDeMedida {
    KILO = 'KILO',
    LITRO = 'LITRO',
    UNIDAD = 'UNIDAD'
}

export interface IProducto {
    id?: number;
    codigo?: string;
    nombre?: string;
    descripcion?: string;
    imagenContentType?: string;
    imagen?: any;
    stock?: number;
    notificacionDeLimiteDeStock?: number;
    unidadDeMedida?: UnidadDeMedida;
    precioVenta?: number;
    precioCompra?: number;
    categoriaNombre?: string;
    categoriaId?: number;
    variantes?: IVariante[];
}

export class Producto implements IProducto {
    constructor(
        public id?: number,
        public codigo?: string,
        public nombre?: string,
        public descripcion?: string,
        public imagenContentType?: string,
        public imagen?: any,
        public stock?: number,
        public notificacionDeLimiteDeStock?: number,
        public unidadDeMedida?: UnidadDeMedida,
        public precioVenta?: number,
        public precioCompra?: number,
        public categoriaNombre?: string,
        public categoriaId?: number,
        public variantes?: IVariante[]
    ) {}
}
