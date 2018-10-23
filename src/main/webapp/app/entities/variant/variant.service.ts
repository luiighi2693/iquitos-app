import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVariant } from 'app/shared/model/variant.model';

type EntityResponseType = HttpResponse<IVariant>;
type EntityArrayResponseType = HttpResponse<IVariant[]>;

@Injectable({ providedIn: 'root' })
export class VariantService {
    public resourceUrl = SERVER_API_URL + 'api/variants';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/variants';

    constructor(private http: HttpClient) {}

    create(variant: IVariant): Observable<EntityResponseType> {
        return this.http.post<IVariant>(this.resourceUrl, variant, { observe: 'response' });
    }

    update(variant: IVariant): Observable<EntityResponseType> {
        return this.http.put<IVariant>(this.resourceUrl, variant, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVariant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVariant[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVariant[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
