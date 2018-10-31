export interface IUnidadDeMedida {
    id?: number;
    nombre?: string;
}

export class UnidadDeMedida implements IUnidadDeMedida {
    constructor(public id?: number, public nombre?: string) {}
}
