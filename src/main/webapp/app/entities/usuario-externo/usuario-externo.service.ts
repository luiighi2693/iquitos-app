import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUsuarioExterno } from 'app/shared/model/usuario-externo.model';

type EntityResponseType = HttpResponse<IUsuarioExterno>;
type EntityArrayResponseType = HttpResponse<IUsuarioExterno[]>;

@Injectable({ providedIn: 'root' })
export class UsuarioExternoService {
    public resourceUrl = SERVER_API_URL + 'api/usuario-externos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/usuario-externos';

    constructor(private http: HttpClient) {}

    create(usuarioExterno: IUsuarioExterno): Observable<EntityResponseType> {
        return this.http.post<IUsuarioExterno>(this.resourceUrl, usuarioExterno, { observe: 'response' });
    }

    update(usuarioExterno: IUsuarioExterno): Observable<EntityResponseType> {
        return this.http.put<IUsuarioExterno>(this.resourceUrl, usuarioExterno, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUsuarioExterno>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUsuarioExterno[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUsuarioExterno[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
