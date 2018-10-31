import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoDeDocumento } from 'app/shared/model/tipo-de-documento.model';

type EntityResponseType = HttpResponse<ITipoDeDocumento>;
type EntityArrayResponseType = HttpResponse<ITipoDeDocumento[]>;

@Injectable({ providedIn: 'root' })
export class TipoDeDocumentoService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-de-documentos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-de-documentos';

    constructor(private http: HttpClient) {}

    create(tipoDeDocumento: ITipoDeDocumento): Observable<EntityResponseType> {
        return this.http.post<ITipoDeDocumento>(this.resourceUrl, tipoDeDocumento, { observe: 'response' });
    }

    update(tipoDeDocumento: ITipoDeDocumento): Observable<EntityResponseType> {
        return this.http.put<ITipoDeDocumento>(this.resourceUrl, tipoDeDocumento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoDeDocumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeDocumento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeDocumento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
