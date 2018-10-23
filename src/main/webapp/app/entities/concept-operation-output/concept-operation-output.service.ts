import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConceptOperationOutput } from 'app/shared/model/concept-operation-output.model';

type EntityResponseType = HttpResponse<IConceptOperationOutput>;
type EntityArrayResponseType = HttpResponse<IConceptOperationOutput[]>;

@Injectable({ providedIn: 'root' })
export class ConceptOperationOutputService {
    public resourceUrl = SERVER_API_URL + 'api/concept-operation-outputs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/concept-operation-outputs';

    constructor(private http: HttpClient) {}

    create(conceptOperationOutput: IConceptOperationOutput): Observable<EntityResponseType> {
        return this.http.post<IConceptOperationOutput>(this.resourceUrl, conceptOperationOutput, { observe: 'response' });
    }

    update(conceptOperationOutput: IConceptOperationOutput): Observable<EntityResponseType> {
        return this.http.put<IConceptOperationOutput>(this.resourceUrl, conceptOperationOutput, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IConceptOperationOutput>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IConceptOperationOutput[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IConceptOperationOutput[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
