import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUnidadDeMedida } from 'app/shared/model/unidad-de-medida.model';

type EntityResponseType = HttpResponse<IUnidadDeMedida>;
type EntityArrayResponseType = HttpResponse<IUnidadDeMedida[]>;

@Injectable({ providedIn: 'root' })
export class UnidadDeMedidaService {
    public resourceUrl = SERVER_API_URL + 'api/unidad-de-medidas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/unidad-de-medidas';

    constructor(private http: HttpClient) {}

    create(unidadDeMedida: IUnidadDeMedida): Observable<EntityResponseType> {
        return this.http.post<IUnidadDeMedida>(this.resourceUrl, unidadDeMedida, { observe: 'response' });
    }

    update(unidadDeMedida: IUnidadDeMedida): Observable<EntityResponseType> {
        return this.http.put<IUnidadDeMedida>(this.resourceUrl, unidadDeMedida, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUnidadDeMedida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUnidadDeMedida[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUnidadDeMedida[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
