import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoDeOperacionDeGasto } from 'app/shared/model/tipo-de-operacion-de-gasto.model';

type EntityResponseType = HttpResponse<ITipoDeOperacionDeGasto>;
type EntityArrayResponseType = HttpResponse<ITipoDeOperacionDeGasto[]>;

@Injectable({ providedIn: 'root' })
export class TipoDeOperacionDeGastoService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-de-operacion-de-gastos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-de-operacion-de-gastos';

    constructor(private http: HttpClient) {}

    create(tipoDeOperacionDeGasto: ITipoDeOperacionDeGasto): Observable<EntityResponseType> {
        return this.http.post<ITipoDeOperacionDeGasto>(this.resourceUrl, tipoDeOperacionDeGasto, { observe: 'response' });
    }

    update(tipoDeOperacionDeGasto: ITipoDeOperacionDeGasto): Observable<EntityResponseType> {
        return this.http.put<ITipoDeOperacionDeGasto>(this.resourceUrl, tipoDeOperacionDeGasto, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoDeOperacionDeGasto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeOperacionDeGasto[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeOperacionDeGasto[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
