export interface IUserLogin {
    id?: number;
    dni?: number;
    pin?: number;
    email?: string;
}

export class UserLogin implements IUserLogin {
    constructor(public id?: number, public dni?: number, public pin?: number, public email?: string) {}
}
