import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstatusDeProductoEntregado } from 'app/shared/model/estatus-de-producto-entregado.model';

type EntityResponseType = HttpResponse<IEstatusDeProductoEntregado>;
type EntityArrayResponseType = HttpResponse<IEstatusDeProductoEntregado[]>;

@Injectable({ providedIn: 'root' })
export class EstatusDeProductoEntregadoService {
    public resourceUrl = SERVER_API_URL + 'api/estatus-de-producto-entregados';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/estatus-de-producto-entregados';

    constructor(private http: HttpClient) {}

    create(estatusDeProductoEntregado: IEstatusDeProductoEntregado): Observable<EntityResponseType> {
        return this.http.post<IEstatusDeProductoEntregado>(this.resourceUrl, estatusDeProductoEntregado, { observe: 'response' });
    }

    update(estatusDeProductoEntregado: IEstatusDeProductoEntregado): Observable<EntityResponseType> {
        return this.http.put<IEstatusDeProductoEntregado>(this.resourceUrl, estatusDeProductoEntregado, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEstatusDeProductoEntregado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstatusDeProductoEntregado[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstatusDeProductoEntregado[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
