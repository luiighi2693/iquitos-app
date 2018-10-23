import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProviderAccount } from 'app/shared/model/provider-account.model';

type EntityResponseType = HttpResponse<IProviderAccount>;
type EntityArrayResponseType = HttpResponse<IProviderAccount[]>;

@Injectable({ providedIn: 'root' })
export class ProviderAccountService {
    public resourceUrl = SERVER_API_URL + 'api/provider-accounts';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/provider-accounts';

    constructor(private http: HttpClient) {}

    create(providerAccount: IProviderAccount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(providerAccount);
        return this.http
            .post<IProviderAccount>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(providerAccount: IProviderAccount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(providerAccount);
        return this.http
            .put<IProviderAccount>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProviderAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProviderAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProviderAccount[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(providerAccount: IProviderAccount): IProviderAccount {
        const copy: IProviderAccount = Object.assign({}, providerAccount, {
            initDate:
                providerAccount.initDate != null && providerAccount.initDate.isValid() ? providerAccount.initDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.initDate = res.body.initDate != null ? moment(res.body.initDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((providerAccount: IProviderAccount) => {
            providerAccount.initDate = providerAccount.initDate != null ? moment(providerAccount.initDate) : null;
        });
        return res;
    }
}
