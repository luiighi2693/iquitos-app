import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICaja } from 'app/shared/model/caja.model';

type EntityResponseType = HttpResponse<ICaja>;
type EntityArrayResponseType = HttpResponse<ICaja[]>;

@Injectable({ providedIn: 'root' })
export class CajaService {
    public resourceUrl = SERVER_API_URL + 'api/cajas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/cajas';

    constructor(private http: HttpClient) {}

    create(caja: ICaja): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(caja);
        return this.http
            .post<ICaja>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(caja: ICaja): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(caja);
        return this.http
            .put<ICaja>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICaja>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICaja[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICaja[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(caja: ICaja): ICaja {
        const copy: ICaja = Object.assign({}, caja, {
            fechaInicial: caja.fechaInicial != null && caja.fechaInicial.isValid() ? caja.fechaInicial.format(DATE_FORMAT) : null,
            fechaFinal: caja.fechaFinal != null && caja.fechaFinal.isValid() ? caja.fechaFinal.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaInicial = res.body.fechaInicial != null ? moment(res.body.fechaInicial) : null;
        res.body.fechaFinal = res.body.fechaFinal != null ? moment(res.body.fechaFinal) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((caja: ICaja) => {
            caja.fechaInicial = caja.fechaInicial != null ? moment(caja.fechaInicial) : null;
            caja.fechaFinal = caja.fechaFinal != null ? moment(caja.fechaFinal) : null;
        });
        return res;
    }
}
