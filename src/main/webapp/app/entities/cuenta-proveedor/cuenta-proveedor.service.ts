import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';

type EntityResponseType = HttpResponse<ICuentaProveedor>;
type EntityArrayResponseType = HttpResponse<ICuentaProveedor[]>;

@Injectable({ providedIn: 'root' })
export class CuentaProveedorService {
    public resourceUrl = SERVER_API_URL + 'api/cuenta-proveedors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/cuenta-proveedors';

    constructor(private http: HttpClient) {}

    create(cuentaProveedor: ICuentaProveedor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cuentaProveedor);
        return this.http
            .post<ICuentaProveedor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(cuentaProveedor: ICuentaProveedor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cuentaProveedor);
        return this.http
            .put<ICuentaProveedor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICuentaProveedor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICuentaProveedor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICuentaProveedor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(cuentaProveedor: ICuentaProveedor): ICuentaProveedor {
        const copy: ICuentaProveedor = Object.assign({}, cuentaProveedor, {
            fecha: cuentaProveedor.fecha != null && cuentaProveedor.fecha.isValid() ? cuentaProveedor.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((cuentaProveedor: ICuentaProveedor) => {
            cuentaProveedor.fecha = cuentaProveedor.fecha != null ? moment(cuentaProveedor.fecha) : null;
        });
        return res;
    }
}
