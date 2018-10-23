import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseStatus } from 'app/shared/model/purchase-status.model';

type EntityResponseType = HttpResponse<IPurchaseStatus>;
type EntityArrayResponseType = HttpResponse<IPurchaseStatus[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseStatusService {
    public resourceUrl = SERVER_API_URL + 'api/purchase-statuses';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/purchase-statuses';

    constructor(private http: HttpClient) {}

    create(purchaseStatus: IPurchaseStatus): Observable<EntityResponseType> {
        return this.http.post<IPurchaseStatus>(this.resourceUrl, purchaseStatus, { observe: 'response' });
    }

    update(purchaseStatus: IPurchaseStatus): Observable<EntityResponseType> {
        return this.http.put<IPurchaseStatus>(this.resourceUrl, purchaseStatus, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPurchaseStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPurchaseStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPurchaseStatus[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
