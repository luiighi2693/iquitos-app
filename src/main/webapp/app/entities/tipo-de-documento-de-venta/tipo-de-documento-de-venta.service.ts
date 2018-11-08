import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoDeDocumentoDeVenta } from 'app/shared/model/tipo-de-documento-de-venta.model';

type EntityResponseType = HttpResponse<ITipoDeDocumentoDeVenta>;
type EntityArrayResponseType = HttpResponse<ITipoDeDocumentoDeVenta[]>;

@Injectable({ providedIn: 'root' })
export class TipoDeDocumentoDeVentaService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-de-documento-de-ventas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-de-documento-de-ventas';

    constructor(private http: HttpClient) {}

    create(tipoDeDocumentoDeVenta: ITipoDeDocumentoDeVenta): Observable<EntityResponseType> {
        return this.http.post<ITipoDeDocumentoDeVenta>(this.resourceUrl, tipoDeDocumentoDeVenta, { observe: 'response' });
    }

    update(tipoDeDocumentoDeVenta: ITipoDeDocumentoDeVenta): Observable<EntityResponseType> {
        return this.http.put<ITipoDeDocumentoDeVenta>(this.resourceUrl, tipoDeDocumentoDeVenta, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoDeDocumentoDeVenta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeDocumentoDeVenta[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeDocumentoDeVenta[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
