import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from '../../shared/util';
import { IContactoProveedor } from '../../models/contacto-proveedor.model';

type EntityResponseType = HttpResponse<IContactoProveedor>;
type EntityArrayResponseType = HttpResponse<IContactoProveedor[]>;

@Injectable({ providedIn: 'root' })
export class ContactoProveedorService {
    public resourceUrl = SERVER_API_URL + 'api/contacto-proveedors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/contacto-proveedors';

    constructor(private http: HttpClient) {}

    create(contactoProveedor: IContactoProveedor): Observable<EntityResponseType> {
        return this.http.post<IContactoProveedor>(this.resourceUrl, contactoProveedor, { observe: 'response' });
    }

    update(contactoProveedor: IContactoProveedor): Observable<EntityResponseType> {
        return this.http.put<IContactoProveedor>(this.resourceUrl, contactoProveedor, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IContactoProveedor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContactoProveedor[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContactoProveedor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
