import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoDeDocumentoDeCompra } from 'app/shared/model/tipo-de-documento-de-compra.model';

type EntityResponseType = HttpResponse<ITipoDeDocumentoDeCompra>;
type EntityArrayResponseType = HttpResponse<ITipoDeDocumentoDeCompra[]>;

@Injectable({ providedIn: 'root' })
export class TipoDeDocumentoDeCompraService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-de-documento-de-compras';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-de-documento-de-compras';

    constructor(private http: HttpClient) {}

    create(tipoDeDocumentoDeCompra: ITipoDeDocumentoDeCompra): Observable<EntityResponseType> {
        return this.http.post<ITipoDeDocumentoDeCompra>(this.resourceUrl, tipoDeDocumentoDeCompra, { observe: 'response' });
    }

    update(tipoDeDocumentoDeCompra: ITipoDeDocumentoDeCompra): Observable<EntityResponseType> {
        return this.http.put<ITipoDeDocumentoDeCompra>(this.resourceUrl, tipoDeDocumentoDeCompra, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoDeDocumentoDeCompra>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeDocumentoDeCompra[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeDocumentoDeCompra[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
