import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVariante } from 'app/shared/model/variante.model';

type EntityResponseType = HttpResponse<IVariante>;
type EntityArrayResponseType = HttpResponse<IVariante[]>;

@Injectable({ providedIn: 'root' })
export class VarianteService {
    public resourceUrl = SERVER_API_URL + 'api/variantes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/variantes';

    constructor(private http: HttpClient) {}

    create(variante: IVariante): Observable<EntityResponseType> {
        return this.http.post<IVariante>(this.resourceUrl, variante, { observe: 'response' });
    }

    update(variante: IVariante): Observable<EntityResponseType> {
        return this.http.put<IVariante>(this.resourceUrl, variante, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVariante>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVariante[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVariante[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
