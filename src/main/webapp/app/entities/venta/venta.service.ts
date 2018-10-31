import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVenta } from 'app/shared/model/venta.model';

type EntityResponseType = HttpResponse<IVenta>;
type EntityArrayResponseType = HttpResponse<IVenta[]>;

@Injectable({ providedIn: 'root' })
export class VentaService {
    public resourceUrl = SERVER_API_URL + 'api/ventas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/ventas';

    constructor(private http: HttpClient) {}

    create(venta: IVenta): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(venta);
        return this.http
            .post<IVenta>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(venta: IVenta): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(venta);
        return this.http
            .put<IVenta>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IVenta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVenta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVenta[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(venta: IVenta): IVenta {
        const copy: IVenta = Object.assign({}, venta, {
            fecha: venta.fecha != null && venta.fecha.isValid() ? venta.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((venta: IVenta) => {
            venta.fecha = venta.fecha != null ? moment(venta.fecha) : null;
        });
        return res;
    }
}
