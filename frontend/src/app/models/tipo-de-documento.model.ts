export interface ITipoDeDocumento {
    id?: number;
    nombre?: string;
}

export class TipoDeDocumento implements ITipoDeDocumento {
    constructor(public id?: number, public nombre?: string) {}
}
