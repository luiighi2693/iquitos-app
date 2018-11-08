import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICredito } from 'app/shared/model/credito.model';

type EntityResponseType = HttpResponse<ICredito>;
type EntityArrayResponseType = HttpResponse<ICredito[]>;

@Injectable({ providedIn: 'root' })
export class CreditoService {
    public resourceUrl = SERVER_API_URL + 'api/creditos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/creditos';

    constructor(private http: HttpClient) {}

    create(credito: ICredito): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(credito);
        return this.http
            .post<ICredito>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(credito: ICredito): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(credito);
        return this.http
            .put<ICredito>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICredito>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICredito[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICredito[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(credito: ICredito): ICredito {
        const copy: ICredito = Object.assign({}, credito, {
            fecha: credito.fecha != null && credito.fecha.isValid() ? credito.fecha.format(DATE_FORMAT) : null,
            fechaLimite: credito.fechaLimite != null && credito.fechaLimite.isValid() ? credito.fechaLimite.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        res.body.fechaLimite = res.body.fechaLimite != null ? moment(res.body.fechaLimite) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((credito: ICredito) => {
            credito.fecha = credito.fecha != null ? moment(credito.fecha) : null;
            credito.fechaLimite = credito.fechaLimite != null ? moment(credito.fechaLimite) : null;
        });
        return res;
    }
}
