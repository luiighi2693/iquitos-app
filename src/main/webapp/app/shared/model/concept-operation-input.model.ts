export interface IConceptOperationInput {
    id?: number;
    value?: string;
    metaData?: string;
}

export class ConceptOperationInput implements IConceptOperationInput {
    constructor(public id?: number, public value?: string, public metaData?: string) {}
}
