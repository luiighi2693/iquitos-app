export interface IUsuarioExterno {
    id?: number;
    dni?: number;
    pin?: number;
    userType?: string;
    role?: string;
}

export class UsuarioExterno implements IUsuarioExterno {
    constructor(public id?: number, public dni?: number, public pin?: number, public userType?: string, public role?: string) {}
}
