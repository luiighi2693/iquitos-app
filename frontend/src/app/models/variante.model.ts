import { IProducto } from './producto.model';

export interface IVariante {
    id?: number;
    nombre?: string;
    descripcion?: string;
    precioVenta?: number;
    precioCompra?: number;
    cantidad?: number;
    productos?: IProducto[];
}

export class Variante implements IVariante {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public precioVenta?: number,
        public precioCompra?: number,
        public cantidad?: number,
        public productos?: IProducto[]
    ) {}
}
