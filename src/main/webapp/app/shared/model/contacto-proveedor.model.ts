export interface IContactoProveedor {
    id?: number;
    nombre?: string;
    cargo?: string;
    producto?: string;
    telefono?: number;
}

export class ContactoProveedor implements IContactoProveedor {
    constructor(public id?: number, public nombre?: string, public cargo?: string, public producto?: string, public telefono?: number) {}
}
