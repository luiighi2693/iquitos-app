import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISell } from 'app/shared/model/sell.model';

type EntityResponseType = HttpResponse<ISell>;
type EntityArrayResponseType = HttpResponse<ISell[]>;

@Injectable({ providedIn: 'root' })
export class SellService {
    public resourceUrl = SERVER_API_URL + 'api/sells';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/sells';

    constructor(private http: HttpClient) {}

    create(sell: ISell): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sell);
        return this.http
            .post<ISell>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(sell: ISell): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sell);
        return this.http
            .put<ISell>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISell>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISell[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISell[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(sell: ISell): ISell {
        const copy: ISell = Object.assign({}, sell, {
            date: sell.date != null && sell.date.isValid() ? sell.date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((sell: ISell) => {
            sell.date = sell.date != null ? moment(sell.date) : null;
        });
        return res;
    }
}
