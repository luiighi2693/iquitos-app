export interface ITipoDeDocumentoDeVenta {
    id?: number;
    nombre?: string;
    serie?: string;
    formato?: string;
}

export class TipoDeDocumentoDeVenta implements ITipoDeDocumentoDeVenta {
    constructor(public id?: number, public nombre?: string, public serie?: string, public formato?: string) {}
}
