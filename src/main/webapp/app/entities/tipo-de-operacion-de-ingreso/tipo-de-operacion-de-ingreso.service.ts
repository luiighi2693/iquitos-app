import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoDeOperacionDeIngreso } from 'app/shared/model/tipo-de-operacion-de-ingreso.model';

type EntityResponseType = HttpResponse<ITipoDeOperacionDeIngreso>;
type EntityArrayResponseType = HttpResponse<ITipoDeOperacionDeIngreso[]>;

@Injectable({ providedIn: 'root' })
export class TipoDeOperacionDeIngresoService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-de-operacion-de-ingresos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-de-operacion-de-ingresos';

    constructor(private http: HttpClient) {}

    create(tipoDeOperacionDeIngreso: ITipoDeOperacionDeIngreso): Observable<EntityResponseType> {
        return this.http.post<ITipoDeOperacionDeIngreso>(this.resourceUrl, tipoDeOperacionDeIngreso, { observe: 'response' });
    }

    update(tipoDeOperacionDeIngreso: ITipoDeOperacionDeIngreso): Observable<EntityResponseType> {
        return this.http.put<ITipoDeOperacionDeIngreso>(this.resourceUrl, tipoDeOperacionDeIngreso, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoDeOperacionDeIngreso>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeOperacionDeIngreso[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeOperacionDeIngreso[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
