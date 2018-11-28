export interface ITipoDeDocumentoDeCompra {
    id?: number;
    nombre?: string;
}

export class TipoDeDocumentoDeCompra implements ITipoDeDocumentoDeCompra {
    constructor(public id?: number, public nombre?: string) {}
}
