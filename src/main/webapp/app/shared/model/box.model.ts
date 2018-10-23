import { Moment } from 'moment';

export interface IBox {
    id?: number;
    initAmount?: number;
    actualAmount?: number;
    initDate?: Moment;
    endDate?: Moment;
    boxId?: number;
}

export class Box implements IBox {
    constructor(
        public id?: number,
        public initAmount?: number,
        public actualAmount?: number,
        public initDate?: Moment,
        public endDate?: Moment,
        public boxId?: number
    ) {}
}
