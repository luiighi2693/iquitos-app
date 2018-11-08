export interface IUsuarioExterno {
    id?: number;
    dni?: number;
    pin?: number;
}

export class UsuarioExterno implements IUsuarioExterno {
    constructor(public id?: number, public dni?: number, public pin?: number) {}
}
