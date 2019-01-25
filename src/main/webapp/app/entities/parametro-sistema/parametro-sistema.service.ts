import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParametroSistema } from 'app/shared/model/parametro-sistema.model';

type EntityResponseType = HttpResponse<IParametroSistema>;
type EntityArrayResponseType = HttpResponse<IParametroSistema[]>;

@Injectable({ providedIn: 'root' })
export class ParametroSistemaService {
    public resourceUrl = SERVER_API_URL + 'api/parametro-sistemas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/parametro-sistemas';

    constructor(private http: HttpClient) {}

    create(parametroSistema: IParametroSistema): Observable<EntityResponseType> {
        return this.http.post<IParametroSistema>(this.resourceUrl, parametroSistema, { observe: 'response' });
    }

    update(parametroSistema: IParametroSistema): Observable<EntityResponseType> {
        return this.http.put<IParametroSistema>(this.resourceUrl, parametroSistema, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IParametroSistema>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParametroSistema[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParametroSistema[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
