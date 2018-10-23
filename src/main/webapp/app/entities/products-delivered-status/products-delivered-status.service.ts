import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductsDeliveredStatus } from 'app/shared/model/products-delivered-status.model';

type EntityResponseType = HttpResponse<IProductsDeliveredStatus>;
type EntityArrayResponseType = HttpResponse<IProductsDeliveredStatus[]>;

@Injectable({ providedIn: 'root' })
export class ProductsDeliveredStatusService {
    public resourceUrl = SERVER_API_URL + 'api/products-delivered-statuses';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/products-delivered-statuses';

    constructor(private http: HttpClient) {}

    create(productsDeliveredStatus: IProductsDeliveredStatus): Observable<EntityResponseType> {
        return this.http.post<IProductsDeliveredStatus>(this.resourceUrl, productsDeliveredStatus, { observe: 'response' });
    }

    update(productsDeliveredStatus: IProductsDeliveredStatus): Observable<EntityResponseType> {
        return this.http.put<IProductsDeliveredStatus>(this.resourceUrl, productsDeliveredStatus, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductsDeliveredStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductsDeliveredStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductsDeliveredStatus[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
