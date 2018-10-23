import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocumentTypePurchase } from 'app/shared/model/document-type-purchase.model';

type EntityResponseType = HttpResponse<IDocumentTypePurchase>;
type EntityArrayResponseType = HttpResponse<IDocumentTypePurchase[]>;

@Injectable({ providedIn: 'root' })
export class DocumentTypePurchaseService {
    public resourceUrl = SERVER_API_URL + 'api/document-type-purchases';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/document-type-purchases';

    constructor(private http: HttpClient) {}

    create(documentTypePurchase: IDocumentTypePurchase): Observable<EntityResponseType> {
        return this.http.post<IDocumentTypePurchase>(this.resourceUrl, documentTypePurchase, { observe: 'response' });
    }

    update(documentTypePurchase: IDocumentTypePurchase): Observable<EntityResponseType> {
        return this.http.put<IDocumentTypePurchase>(this.resourceUrl, documentTypePurchase, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDocumentTypePurchase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDocumentTypePurchase[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDocumentTypePurchase[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
