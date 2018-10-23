export interface IDocumentTypeSell {
    id?: number;
    value?: string;
    metaData?: string;
}

export class DocumentTypeSell implements IDocumentTypeSell {
    constructor(public id?: number, public value?: string, public metaData?: string) {}
}
