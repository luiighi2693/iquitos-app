export interface ITipoDePago {
    id?: number;
    nombre?: string;
}

export class TipoDePago implements ITipoDePago {
    constructor(public id?: number, public nombre?: string) {}
}
