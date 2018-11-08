import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProducto } from 'app/shared/model/producto.model';

type EntityResponseType = HttpResponse<IProducto>;
type EntityArrayResponseType = HttpResponse<IProducto[]>;

@Injectable({ providedIn: 'root' })
export class ProductoService {
    public resourceUrl = SERVER_API_URL + 'api/productos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/productos';

    constructor(private http: HttpClient) {}

    create(producto: IProducto): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(producto);
        return this.http
            .post<IProducto>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(producto: IProducto): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(producto);
        return this.http
            .put<IProducto>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProducto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProducto[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(producto: IProducto): IProducto {
        const copy: IProducto = Object.assign({}, producto, {
            fechaExpiracion:
                producto.fechaExpiracion != null && producto.fechaExpiracion.isValid() ? producto.fechaExpiracion.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fechaExpiracion = res.body.fechaExpiracion != null ? moment(res.body.fechaExpiracion) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((producto: IProducto) => {
                producto.fechaExpiracion = producto.fechaExpiracion != null ? moment(producto.fechaExpiracion) : null;
            });
        }
        return res;
    }
}
