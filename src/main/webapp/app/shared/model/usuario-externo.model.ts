export interface IUsuarioExterno {
    id?: number;
    dni?: number;
    pin?: number;
    usuarioLogin?: string;
    usuarioId?: number;
}

export class UsuarioExterno implements IUsuarioExterno {
    constructor(public id?: number, public dni?: number, public pin?: number, public usuarioLogin?: string, public usuarioId?: number) {}
}
