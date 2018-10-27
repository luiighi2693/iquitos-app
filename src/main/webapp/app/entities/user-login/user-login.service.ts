import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserLogin } from 'app/shared/model/user-login.model';

type EntityResponseType = HttpResponse<IUserLogin>;
type EntityArrayResponseType = HttpResponse<IUserLogin[]>;

@Injectable({ providedIn: 'root' })
export class UserLoginService {
    public resourceUrl = SERVER_API_URL + 'api/user-logins';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/user-logins';

    constructor(private http: HttpClient) {}

    create(userLogin: IUserLogin): Observable<EntityResponseType> {
        return this.http.post<IUserLogin>(this.resourceUrl, userLogin, { observe: 'response' });
    }

    update(userLogin: IUserLogin): Observable<EntityResponseType> {
        return this.http.put<IUserLogin>(this.resourceUrl, userLogin, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserLogin>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserLogin[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserLogin[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
