export interface ICategory {
    id?: number;
    name?: string;
    countProducts?: number;
}

export class Category implements ICategory {
    constructor(public id?: number, public name?: string, public countProducts?: number) {}
}
