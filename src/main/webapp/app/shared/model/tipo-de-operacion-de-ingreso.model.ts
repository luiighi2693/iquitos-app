export interface ITipoDeOperacionDeIngreso {
    id?: number;
    nombre?: string;
}

export class TipoDeOperacionDeIngreso implements ITipoDeOperacionDeIngreso {
    constructor(public id?: number, public nombre?: string) {}
}
