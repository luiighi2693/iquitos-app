import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBox } from 'app/shared/model/box.model';

type EntityResponseType = HttpResponse<IBox>;
type EntityArrayResponseType = HttpResponse<IBox[]>;

@Injectable({ providedIn: 'root' })
export class BoxService {
    public resourceUrl = SERVER_API_URL + 'api/boxes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/boxes';

    constructor(private http: HttpClient) {}

    create(box: IBox): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(box);
        return this.http
            .post<IBox>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(box: IBox): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(box);
        return this.http
            .put<IBox>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBox>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBox[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBox[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(box: IBox): IBox {
        const copy: IBox = Object.assign({}, box, {
            initDate: box.initDate != null && box.initDate.isValid() ? box.initDate.format(DATE_FORMAT) : null,
            endDate: box.endDate != null && box.endDate.isValid() ? box.endDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.initDate = res.body.initDate != null ? moment(res.body.initDate) : null;
        res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((box: IBox) => {
            box.initDate = box.initDate != null ? moment(box.initDate) : null;
            box.endDate = box.endDate != null ? moment(box.endDate) : null;
        });
        return res;
    }
}
