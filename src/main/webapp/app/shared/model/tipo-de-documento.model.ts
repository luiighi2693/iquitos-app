export interface ITipoDeDocumento {
    id?: number;
    nombre?: string;
    cantidadDigitos?: number;
}

export class TipoDeDocumento implements ITipoDeDocumento {
    constructor(public id?: number, public nombre?: string, public cantidadDigitos?: number) {}
}
