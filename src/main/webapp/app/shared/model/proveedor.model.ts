import { ICuentaProveedor } from 'app/shared/model//cuenta-proveedor.model';
import { IContactoProveedor } from 'app/shared/model//contacto-proveedor.model';

export interface IProveedor {
    id?: number;
    codigo?: string;
    razonSocial?: string;
    direccion?: string;
    correo?: string;
    telefono?: string;
    cuentaProveedors?: ICuentaProveedor[];
    contactoProveedors?: IContactoProveedor[];
}

export class Proveedor implements IProveedor {
    constructor(
        public id?: number,
        public codigo?: string,
        public razonSocial?: string,
        public direccion?: string,
        public correo?: string,
        public telefono?: string,
        public cuentaProveedors?: ICuentaProveedor[],
        public contactoProveedors?: IContactoProveedor[]
    ) {}
}
