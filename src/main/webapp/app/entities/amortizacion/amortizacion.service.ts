import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmortizacion } from 'app/shared/model/amortizacion.model';

type EntityResponseType = HttpResponse<IAmortizacion>;
type EntityArrayResponseType = HttpResponse<IAmortizacion[]>;

@Injectable({ providedIn: 'root' })
export class AmortizacionService {
    public resourceUrl = SERVER_API_URL + 'api/amortizacions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/amortizacions';

    constructor(private http: HttpClient) {}

    create(amortizacion: IAmortizacion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(amortizacion);
        return this.http
            .post<IAmortizacion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(amortizacion: IAmortizacion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(amortizacion);
        return this.http
            .put<IAmortizacion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAmortizacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAmortizacion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAmortizacion[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(amortizacion: IAmortizacion): IAmortizacion {
        const copy: IAmortizacion = Object.assign({}, amortizacion, {
            fecha: amortizacion.fecha != null && amortizacion.fecha.isValid() ? amortizacion.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((amortizacion: IAmortizacion) => {
                amortizacion.fecha = amortizacion.fecha != null ? moment(amortizacion.fecha) : null;
            });
        }
        return res;
    }
}
