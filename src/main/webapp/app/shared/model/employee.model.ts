import { Moment } from 'moment';

export const enum Sex {
    MASCULINO = 'MASCULINO',
    FEMENINO = 'FEMENINO'
}

export const enum EmployeeRole {
    VENDEDOR = 'VENDEDOR'
}

export interface IEmployee {
    id?: number;
    firstName?: string;
    lastName?: string;
    dni?: number;
    address?: string;
    email?: string;
    birthday?: Moment;
    sex?: Sex;
    cellphone?: number;
    imageContentType?: string;
    image?: any;
    employeeRole?: EmployeeRole;
    userId?: number;
}

export class Employee implements IEmployee {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public dni?: number,
        public address?: string,
        public email?: string,
        public birthday?: Moment,
        public sex?: Sex,
        public cellphone?: number,
        public imageContentType?: string,
        public image?: any,
        public employeeRole?: EmployeeRole,
        public userId?: number
    ) {}
}
