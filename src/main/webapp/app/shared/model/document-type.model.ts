export interface IDocumentType {
    id?: number;
    value?: string;
    metaData?: string;
}

export class DocumentType implements IDocumentType {
    constructor(public id?: number, public value?: string, public metaData?: string) {}
}
