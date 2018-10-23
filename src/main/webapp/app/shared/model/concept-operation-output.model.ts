export interface IConceptOperationOutput {
    id?: number;
    value?: string;
    metaData?: string;
}

export class ConceptOperationOutput implements IConceptOperationOutput {
    constructor(public id?: number, public value?: string, public metaData?: string) {}
}
