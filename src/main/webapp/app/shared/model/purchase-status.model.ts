export interface IPurchaseStatus {
    id?: number;
    value?: string;
    metaData?: string;
}

export class PurchaseStatus implements IPurchaseStatus {
    constructor(public id?: number, public value?: string, public metaData?: string) {}
}
