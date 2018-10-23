export interface IDocumentTypePurchase {
    id?: number;
    value?: string;
    metaData?: string;
}

export class DocumentTypePurchase implements IDocumentTypePurchase {
    constructor(public id?: number, public value?: string, public metaData?: string) {}
}
