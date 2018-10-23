import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICredit } from 'app/shared/model/credit.model';

type EntityResponseType = HttpResponse<ICredit>;
type EntityArrayResponseType = HttpResponse<ICredit[]>;

@Injectable({ providedIn: 'root' })
export class CreditService {
    public resourceUrl = SERVER_API_URL + 'api/credits';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/credits';

    constructor(private http: HttpClient) {}

    create(credit: ICredit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(credit);
        return this.http
            .post<ICredit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(credit: ICredit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(credit);
        return this.http
            .put<ICredit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICredit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICredit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICredit[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(credit: ICredit): ICredit {
        const copy: ICredit = Object.assign({}, credit, {
            emissionDate: credit.emissionDate != null && credit.emissionDate.isValid() ? credit.emissionDate.format(DATE_FORMAT) : null,
            limitDate: credit.limitDate != null && credit.limitDate.isValid() ? credit.limitDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.emissionDate = res.body.emissionDate != null ? moment(res.body.emissionDate) : null;
        res.body.limitDate = res.body.limitDate != null ? moment(res.body.limitDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((credit: ICredit) => {
            credit.emissionDate = credit.emissionDate != null ? moment(credit.emissionDate) : null;
            credit.limitDate = credit.limitDate != null ? moment(credit.limitDate) : null;
        });
        return res;
    }
}
