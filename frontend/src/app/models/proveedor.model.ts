// import { ICuentaProveedor } from 'app/shared/model//cuenta-proveedor.model';

import { IContactoProveedor } from './contactoproveedor.model';

export interface IProveedor {
    id?: number;
    codigo?: string;
    razonSocial?: string;
    direccion?: string;
    correo?: string;
    telefono?: string;
  productosRelacionados?: string;
  contactos?: IContactoProveedor[];
    // cuentaProveedors?: ICuentaProveedor[];
}

export class Proveedor implements IProveedor {
    constructor(
        public id?: number,
        public codigo?: string,
        public razonSocial?: string,
        public direccion?: string,
        public correo?: string,
        public telefono?: string,
        public productosRelacionados?: string,
        public contactos?: IContactoProveedor[],
        // public cuentaProveedors?: ICuentaProveedor[]
    ) {}
}
