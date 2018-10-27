import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderProduct } from 'app/shared/model/order-product.model';

type EntityResponseType = HttpResponse<IOrderProduct>;
type EntityArrayResponseType = HttpResponse<IOrderProduct[]>;

@Injectable({ providedIn: 'root' })
export class OrderProductService {
    public resourceUrl = SERVER_API_URL + 'api/order-products';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/order-products';

    constructor(private http: HttpClient) {}

    create(orderProduct: IOrderProduct): Observable<EntityResponseType> {
        return this.http.post<IOrderProduct>(this.resourceUrl, orderProduct, { observe: 'response' });
    }

    update(orderProduct: IOrderProduct): Observable<EntityResponseType> {
        return this.http.put<IOrderProduct>(this.resourceUrl, orderProduct, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOrderProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOrderProduct[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOrderProduct[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
