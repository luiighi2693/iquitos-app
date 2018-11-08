import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPagoDeProveedor } from 'app/shared/model/pago-de-proveedor.model';

type EntityResponseType = HttpResponse<IPagoDeProveedor>;
type EntityArrayResponseType = HttpResponse<IPagoDeProveedor[]>;

@Injectable({ providedIn: 'root' })
export class PagoDeProveedorService {
    public resourceUrl = SERVER_API_URL + 'api/pago-de-proveedors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/pago-de-proveedors';

    constructor(private http: HttpClient) {}

    create(pagoDeProveedor: IPagoDeProveedor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pagoDeProveedor);
        return this.http
            .post<IPagoDeProveedor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pagoDeProveedor: IPagoDeProveedor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pagoDeProveedor);
        return this.http
            .put<IPagoDeProveedor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPagoDeProveedor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPagoDeProveedor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPagoDeProveedor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(pagoDeProveedor: IPagoDeProveedor): IPagoDeProveedor {
        const copy: IPagoDeProveedor = Object.assign({}, pagoDeProveedor, {
            fecha: pagoDeProveedor.fecha != null && pagoDeProveedor.fecha.isValid() ? pagoDeProveedor.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pagoDeProveedor: IPagoDeProveedor) => {
            pagoDeProveedor.fecha = pagoDeProveedor.fecha != null ? moment(pagoDeProveedor.fecha) : null;
        });
        return res;
    }
}
