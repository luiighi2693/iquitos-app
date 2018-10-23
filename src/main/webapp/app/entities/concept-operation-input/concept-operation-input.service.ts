import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConceptOperationInput } from 'app/shared/model/concept-operation-input.model';

type EntityResponseType = HttpResponse<IConceptOperationInput>;
type EntityArrayResponseType = HttpResponse<IConceptOperationInput[]>;

@Injectable({ providedIn: 'root' })
export class ConceptOperationInputService {
    public resourceUrl = SERVER_API_URL + 'api/concept-operation-inputs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/concept-operation-inputs';

    constructor(private http: HttpClient) {}

    create(conceptOperationInput: IConceptOperationInput): Observable<EntityResponseType> {
        return this.http.post<IConceptOperationInput>(this.resourceUrl, conceptOperationInput, { observe: 'response' });
    }

    update(conceptOperationInput: IConceptOperationInput): Observable<EntityResponseType> {
        return this.http.put<IConceptOperationInput>(this.resourceUrl, conceptOperationInput, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IConceptOperationInput>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IConceptOperationInput[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IConceptOperationInput[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
