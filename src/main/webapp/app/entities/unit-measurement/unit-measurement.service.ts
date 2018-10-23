import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUnitMeasurement } from 'app/shared/model/unit-measurement.model';

type EntityResponseType = HttpResponse<IUnitMeasurement>;
type EntityArrayResponseType = HttpResponse<IUnitMeasurement[]>;

@Injectable({ providedIn: 'root' })
export class UnitMeasurementService {
    public resourceUrl = SERVER_API_URL + 'api/unit-measurements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/unit-measurements';

    constructor(private http: HttpClient) {}

    create(unitMeasurement: IUnitMeasurement): Observable<EntityResponseType> {
        return this.http.post<IUnitMeasurement>(this.resourceUrl, unitMeasurement, { observe: 'response' });
    }

    update(unitMeasurement: IUnitMeasurement): Observable<EntityResponseType> {
        return this.http.put<IUnitMeasurement>(this.resourceUrl, unitMeasurement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUnitMeasurement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUnitMeasurement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUnitMeasurement[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
