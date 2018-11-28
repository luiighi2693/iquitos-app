export interface IEstatusDeProductoEntregado {
    id?: number;
    nombre?: string;
}

export class EstatusDeProductoEntregado implements IEstatusDeProductoEntregado {
    constructor(public id?: number, public nombre?: string) {}
}
