import { Moment } from 'moment';

export const enum OperationType {
    INGRESO = 'INGRESO',
    EGRESO = 'EGRESO'
}

export interface IOperation {
    id?: number;
    initDate?: Moment;
    gloss?: string;
    amount?: number;
    operationType?: OperationType;
    boxId?: number;
    paymentTypeId?: number;
    conceptOperationInputId?: number;
    conceptOperationOutputId?: number;
}

export class Operation implements IOperation {
    constructor(
        public id?: number,
        public initDate?: Moment,
        public gloss?: string,
        public amount?: number,
        public operationType?: OperationType,
        public boxId?: number,
        public paymentTypeId?: number,
        public conceptOperationInputId?: number,
        public conceptOperationOutputId?: number
    ) {}
}
