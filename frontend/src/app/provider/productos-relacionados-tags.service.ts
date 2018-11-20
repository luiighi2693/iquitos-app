import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from '../shared/util';
import { IProductosRelacionadosTags } from '../models/productos-relacionados-tags.model';

type EntityResponseType = HttpResponse<IProductosRelacionadosTags>;
type EntityArrayResponseType = HttpResponse<IProductosRelacionadosTags[]>;

@Injectable({ providedIn: 'root' })
export class ProductosRelacionadosTagsService {
    public resourceUrl = SERVER_API_URL + 'api/productos-relacionados-tags';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/productos-relacionados-tags';

    constructor(private http: HttpClient) {}

    create(productosRelacionadosTags: IProductosRelacionadosTags): Observable<EntityResponseType> {
        return this.http.post<IProductosRelacionadosTags>(this.resourceUrl, productosRelacionadosTags, { observe: 'response' });
    }

    update(productosRelacionadosTags: IProductosRelacionadosTags): Observable<EntityResponseType> {
        return this.http.put<IProductosRelacionadosTags>(this.resourceUrl, productosRelacionadosTags, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductosRelacionadosTags>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductosRelacionadosTags[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductosRelacionadosTags[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
