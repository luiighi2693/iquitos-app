export interface IEstatusDeCompra {
    id?: number;
    nombre?: string;
}

export class EstatusDeCompra implements IEstatusDeCompra {
    constructor(public id?: number, public nombre?: string) {}
}
