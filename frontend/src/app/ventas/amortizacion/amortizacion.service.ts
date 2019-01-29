import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from '../../shared/util';
import { IAmortizacion } from '../../models/amortizacion.model';

type EntityResponseType = HttpResponse<IAmortizacion>;
type EntityArrayResponseType = HttpResponse<IAmortizacion[]>;

@Injectable({ providedIn: 'root' })
export class AmortizacionService {
    public resourceUrl = SERVER_API_URL + 'api/amortizacions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/amortizacions';

    constructor(private http: HttpClient) {}

    create(amortizacion: IAmortizacion): Observable<EntityResponseType> {
        return this.http
            .post<IAmortizacion>(this.resourceUrl, amortizacion, { observe: 'response' });
    }

    update(amortizacion: IAmortizacion): Observable<EntityResponseType> {
        return this.http
            .put<IAmortizacion>(this.resourceUrl, amortizacion, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAmortizacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAmortizacion[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAmortizacion[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
