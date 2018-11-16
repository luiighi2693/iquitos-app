export interface IContactoProveedor {
    id?: number;
    nombre?: string;
    cargo?: string;
    telefono?: string;
    producto?: string;
}

export class ContactoProveedor implements IContactoProveedor {
    constructor(
        public id?: number,
        public nombre?: string,
        public cargo?: string,
        public telefono?: string,
        public producto?: string,
    ) {}
}
