import { IVariante } from 'app/shared/model//variante.model';
import { IProducto } from 'app/shared/model//producto.model';

export interface IProductoDetalle {
    id?: number;
    cantidad?: number;
    productoLabel?: string;
    precioVenta?: number;
    variantes?: IVariante[];
    productos?: IProducto[];
}

export class ProductoDetalle implements IProductoDetalle {
    constructor(
        public id?: number,
        public cantidad?: number,
        public productoLabel?: string,
        public precioVenta?: number,
        public variantes?: IVariante[],
        public productos?: IProducto[]
    ) {}
}
