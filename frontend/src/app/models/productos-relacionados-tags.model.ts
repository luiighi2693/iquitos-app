export interface IProductosRelacionadosTags {
    id?: number;
    nombre?: string;
}

export class ProductosRelacionadosTags implements IProductosRelacionadosTags {
    constructor(public id?: number, public nombre?: string) {}
}
