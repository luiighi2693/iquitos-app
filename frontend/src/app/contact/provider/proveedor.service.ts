import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from '../../shared/util';
import { IProveedor } from '../../models/proveedor.model';

type EntityResponseType = HttpResponse<IProveedor>;
type EntityArrayResponseType = HttpResponse<IProveedor[]>;

@Injectable({ providedIn: 'root' })
export class ProveedorService {
    public resourceUrl = SERVER_API_URL + 'api/proveedors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/proveedors';

    constructor(private http: HttpClient) {}

    create(proveedor: IProveedor): Observable<EntityResponseType> {
        return this.http.post<IProveedor>(this.resourceUrl, proveedor, { observe: 'response' });
    }

    update(proveedor: IProveedor): Observable<EntityResponseType> {
        return this.http.put<IProveedor>(this.resourceUrl, proveedor, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProveedor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProveedor[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProveedor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
