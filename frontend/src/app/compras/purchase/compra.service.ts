import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from '../../shared/util';
import { ICompra } from '../../models/compra.model';

type EntityResponseType = HttpResponse<ICompra>;
type EntityArrayResponseType = HttpResponse<ICompra[]>;

@Injectable({ providedIn: 'root' })
export class CompraService {
    public resourceUrl = SERVER_API_URL + 'api/compras';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/compras';

    constructor(private http: HttpClient) {}

    create(compra: ICompra): Observable<EntityResponseType> {
        return this.http
            .post<ICompra>(this.resourceUrl, compra, { observe: 'response' });
    }

    update(compra: ICompra): Observable<EntityResponseType> {
        return this.http
            .put<ICompra>(this.resourceUrl, compra, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICompra>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICompra[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICompra[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((compra: ICompra) => {
                compra.fecha = compra.fecha != null ? moment(compra.fecha) : null;
            });
        }
        return res;
    }
}
