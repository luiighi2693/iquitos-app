import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseHasProduct } from 'app/shared/model/purchase-has-product.model';

type EntityResponseType = HttpResponse<IPurchaseHasProduct>;
type EntityArrayResponseType = HttpResponse<IPurchaseHasProduct[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseHasProductService {
    public resourceUrl = SERVER_API_URL + 'api/purchase-has-products';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/purchase-has-products';

    constructor(private http: HttpClient) {}

    create(purchaseHasProduct: IPurchaseHasProduct): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseHasProduct);
        return this.http
            .post<IPurchaseHasProduct>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(purchaseHasProduct: IPurchaseHasProduct): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseHasProduct);
        return this.http
            .put<IPurchaseHasProduct>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPurchaseHasProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseHasProduct[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseHasProduct[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(purchaseHasProduct: IPurchaseHasProduct): IPurchaseHasProduct {
        const copy: IPurchaseHasProduct = Object.assign({}, purchaseHasProduct, {
            datePurchase:
                purchaseHasProduct.datePurchase != null && purchaseHasProduct.datePurchase.isValid()
                    ? purchaseHasProduct.datePurchase.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.datePurchase = res.body.datePurchase != null ? moment(res.body.datePurchase) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((purchaseHasProduct: IPurchaseHasProduct) => {
            purchaseHasProduct.datePurchase = purchaseHasProduct.datePurchase != null ? moment(purchaseHasProduct.datePurchase) : null;
        });
        return res;
    }
}
