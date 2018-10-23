export interface IUnitMeasurement {
    id?: number;
    value?: string;
    metaData?: string;
}

export class UnitMeasurement implements IUnitMeasurement {
    constructor(public id?: number, public value?: string, public metaData?: string) {}
}
