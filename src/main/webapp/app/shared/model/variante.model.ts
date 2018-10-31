import { IProducto } from 'app/shared/model//producto.model';

export interface IVariante {
    id?: number;
    nombre?: string;
    descripcion?: string;
    precioVenta?: number;
    precioCompra?: number;
    productos?: IProducto[];
}

export class Variante implements IVariante {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public precioVenta?: number,
        public precioCompra?: number,
        public productos?: IProducto[]
    ) {}
}
