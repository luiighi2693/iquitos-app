export interface ITipoDeOperacionDeGasto {
    id?: number;
    nombre?: string;
}

export class TipoDeOperacionDeGasto implements ITipoDeOperacionDeGasto {
    constructor(public id?: number, public nombre?: string) {}
}
