import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISellHasProduct } from 'app/shared/model/sell-has-product.model';

type EntityResponseType = HttpResponse<ISellHasProduct>;
type EntityArrayResponseType = HttpResponse<ISellHasProduct[]>;

@Injectable({ providedIn: 'root' })
export class SellHasProductService {
    public resourceUrl = SERVER_API_URL + 'api/sell-has-products';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/sell-has-products';

    constructor(private http: HttpClient) {}

    create(sellHasProduct: ISellHasProduct): Observable<EntityResponseType> {
        return this.http.post<ISellHasProduct>(this.resourceUrl, sellHasProduct, { observe: 'response' });
    }

    update(sellHasProduct: ISellHasProduct): Observable<EntityResponseType> {
        return this.http.put<ISellHasProduct>(this.resourceUrl, sellHasProduct, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISellHasProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISellHasProduct[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISellHasProduct[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
