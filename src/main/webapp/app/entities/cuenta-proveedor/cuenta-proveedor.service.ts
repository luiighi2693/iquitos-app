import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICuentaProveedor } from 'app/shared/model/cuenta-proveedor.model';

type EntityResponseType = HttpResponse<ICuentaProveedor>;
type EntityArrayResponseType = HttpResponse<ICuentaProveedor[]>;

@Injectable({ providedIn: 'root' })
export class CuentaProveedorService {
    public resourceUrl = SERVER_API_URL + 'api/cuenta-proveedors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/cuenta-proveedors';

    constructor(private http: HttpClient) {}

    create(cuentaProveedor: ICuentaProveedor): Observable<EntityResponseType> {
        return this.http.post<ICuentaProveedor>(this.resourceUrl, cuentaProveedor, { observe: 'response' });
    }

    update(cuentaProveedor: ICuentaProveedor): Observable<EntityResponseType> {
        return this.http.put<ICuentaProveedor>(this.resourceUrl, cuentaProveedor, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICuentaProveedor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICuentaProveedor[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICuentaProveedor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
