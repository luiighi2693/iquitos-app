export interface ICategoria {
    id?: number;
    nombre?: string;
    numeroProductos?: number;
}

export class Categoria implements ICategoria {
    constructor(public id?: number, public nombre?: string, public numeroProductos?: number) {}
}
