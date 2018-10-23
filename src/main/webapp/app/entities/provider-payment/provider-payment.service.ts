import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProviderPayment } from 'app/shared/model/provider-payment.model';

type EntityResponseType = HttpResponse<IProviderPayment>;
type EntityArrayResponseType = HttpResponse<IProviderPayment[]>;

@Injectable({ providedIn: 'root' })
export class ProviderPaymentService {
    public resourceUrl = SERVER_API_URL + 'api/provider-payments';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/provider-payments';

    constructor(private http: HttpClient) {}

    create(providerPayment: IProviderPayment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(providerPayment);
        return this.http
            .post<IProviderPayment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(providerPayment: IProviderPayment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(providerPayment);
        return this.http
            .put<IProviderPayment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProviderPayment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProviderPayment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProviderPayment[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(providerPayment: IProviderPayment): IProviderPayment {
        const copy: IProviderPayment = Object.assign({}, providerPayment, {
            emissionDate:
                providerPayment.emissionDate != null && providerPayment.emissionDate.isValid()
                    ? providerPayment.emissionDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.emissionDate = res.body.emissionDate != null ? moment(res.body.emissionDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((providerPayment: IProviderPayment) => {
            providerPayment.emissionDate = providerPayment.emissionDate != null ? moment(providerPayment.emissionDate) : null;
        });
        return res;
    }
}
