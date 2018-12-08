export const enum UserType {
    ADMINISTRADOR = 'ADMINISTRADOR',
    EMPLEADO = 'EMPLEADO',
    CLIENTE = 'CLIENTE',
    PROVEEDOR = 'PROVEEDOR'
}

export interface IUsuarioExterno {
    id?: number;
    dni?: number;
    pin?: number;
    idEntity?: number;
    userType?: UserType;
    role?: string;
}

export class UsuarioExterno implements IUsuarioExterno {
    constructor(
        public id?: number,
        public dni?: number,
        public pin?: number,
        public idEntity?: number,
        public userType?: UserType,
        public role?: string
    ) {}
}
