import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstatusDeCompra } from 'app/shared/model/estatus-de-compra.model';

type EntityResponseType = HttpResponse<IEstatusDeCompra>;
type EntityArrayResponseType = HttpResponse<IEstatusDeCompra[]>;

@Injectable({ providedIn: 'root' })
export class EstatusDeCompraService {
    public resourceUrl = SERVER_API_URL + 'api/estatus-de-compras';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/estatus-de-compras';

    constructor(private http: HttpClient) {}

    create(estatusDeCompra: IEstatusDeCompra): Observable<EntityResponseType> {
        return this.http.post<IEstatusDeCompra>(this.resourceUrl, estatusDeCompra, { observe: 'response' });
    }

    update(estatusDeCompra: IEstatusDeCompra): Observable<EntityResponseType> {
        return this.http.put<IEstatusDeCompra>(this.resourceUrl, estatusDeCompra, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEstatusDeCompra>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstatusDeCompra[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstatusDeCompra[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
