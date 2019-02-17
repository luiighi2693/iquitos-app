import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';


type EntityResponseType = HttpResponse<any>;

@Injectable({ providedIn: 'root' })
export class RucService {
    public resourceUrl = 'http://api.sunat.cloud/ruc';

    constructor(private http: HttpClient) {}

    find(ruc: number): Observable<EntityResponseType> {
        return this.http.post<any>(`${this.resourceUrl}/${ruc}`, { observe: 'response' });
    }
}
