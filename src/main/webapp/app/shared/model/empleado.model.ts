import { Moment } from 'moment';

export const enum Sex {
    MASCULINO = 'MASCULINO',
    FEMENINO = 'FEMENINO'
}

export const enum EmployeeRole {
    VENDEDOR = 'VENDEDOR'
}

export interface IEmpleado {
    id?: number;
    nombre?: string;
    apellido?: string;
    dni?: number;
    direccion?: string;
    correo?: string;
    fechaDeNacimiento?: Moment;
    sexo?: Sex;
    telefono?: string;
    imagenContentType?: string;
    imagen?: any;
    rolEmpleado?: EmployeeRole;
    usuarioDni?: string;
    usuarioId?: number;
}

export class Empleado implements IEmpleado {
    constructor(
        public id?: number,
        public nombre?: string,
        public apellido?: string,
        public dni?: number,
        public direccion?: string,
        public correo?: string,
        public fechaDeNacimiento?: Moment,
        public sexo?: Sex,
        public telefono?: string,
        public imagenContentType?: string,
        public imagen?: any,
        public rolEmpleado?: EmployeeRole,
        public usuarioDni?: string,
        public usuarioId?: number
    ) {}
}
