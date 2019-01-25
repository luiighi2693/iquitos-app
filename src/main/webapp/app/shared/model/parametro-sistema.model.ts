export interface IParametroSistema {
    id?: number;
    nombre?: string;
}

export class ParametroSistema implements IParametroSistema {
    constructor(public id?: number, public nombre?: string) {}
}
