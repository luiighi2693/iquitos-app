import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from '../../shared/util';
import { ITipoDePago } from '../../models/tipo-de-pago.model';

type EntityResponseType = HttpResponse<ITipoDePago>;
type EntityArrayResponseType = HttpResponse<ITipoDePago[]>;

@Injectable({ providedIn: 'root' })
export class TipoDePagoService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-de-pagos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-de-pagos';

    constructor(private http: HttpClient) {}

    create(tipoDePago: ITipoDePago): Observable<EntityResponseType> {
        return this.http.post<ITipoDePago>(this.resourceUrl, tipoDePago, { observe: 'response' });
    }

    update(tipoDePago: ITipoDePago): Observable<EntityResponseType> {
        return this.http.put<ITipoDePago>(this.resourceUrl, tipoDePago, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoDePago>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDePago[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDePago[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
