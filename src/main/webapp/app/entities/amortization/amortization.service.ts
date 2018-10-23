import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmortization } from 'app/shared/model/amortization.model';

type EntityResponseType = HttpResponse<IAmortization>;
type EntityArrayResponseType = HttpResponse<IAmortization[]>;

@Injectable({ providedIn: 'root' })
export class AmortizationService {
    public resourceUrl = SERVER_API_URL + 'api/amortizations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/amortizations';

    constructor(private http: HttpClient) {}

    create(amortization: IAmortization): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(amortization);
        return this.http
            .post<IAmortization>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(amortization: IAmortization): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(amortization);
        return this.http
            .put<IAmortization>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAmortization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAmortization[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAmortization[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(amortization: IAmortization): IAmortization {
        const copy: IAmortization = Object.assign({}, amortization, {
            emissionDate:
                amortization.emissionDate != null && amortization.emissionDate.isValid()
                    ? amortization.emissionDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.emissionDate = res.body.emissionDate != null ? moment(res.body.emissionDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((amortization: IAmortization) => {
            amortization.emissionDate = amortization.emissionDate != null ? moment(amortization.emissionDate) : null;
        });
        return res;
    }
}
