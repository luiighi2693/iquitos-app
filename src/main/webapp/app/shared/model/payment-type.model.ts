export interface IPaymentType {
    id?: number;
    value?: string;
    metaData?: string;
}

export class PaymentType implements IPaymentType {
    constructor(public id?: number, public value?: string, public metaData?: string) {}
}
