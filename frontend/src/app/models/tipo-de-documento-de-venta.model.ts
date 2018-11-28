export interface ITipoDeDocumentoDeVenta {
    id?: number;
    nombre?: string;
}

export class TipoDeDocumentoDeVenta implements ITipoDeDocumentoDeVenta {
    constructor(public id?: number, public nombre?: string) {}
}
