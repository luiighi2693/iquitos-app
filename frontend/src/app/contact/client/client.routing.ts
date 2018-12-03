import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { ClientComponent } from './client.component';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { ClientDetailComponent } from './client-detail.component';
import { ClientUpdateComponent } from './client-update.component';
import {ClienteService} from "./cliente.service";
import {Cliente, ICliente} from "../../models/cliente.model";

@Injectable({ providedIn: 'root' })
export class ClienteResolve implements Resolve<ICliente> {
  constructor(private service: ClienteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Cliente> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cliente>) => response.ok),
        map((entity: HttpResponse<Cliente>) => entity.body)
      );
    }
    return of(new Cliente());
  }
}

export const ClientRoutes: Routes = [
  {
    path: '',
    component: ClientComponent
  },
  {
    path: ':id/view',
    component: ClientDetailComponent,
    resolve: {
      entity: ClienteResolve
    }
  },
  {
    path: 'new',
    component: ClientUpdateComponent,
    resolve: {
      entity: ClienteResolve
    }
  },
  {
    path: ':id/edit',
    component: ClientUpdateComponent,
    resolve: {
      entity: ClienteResolve
    }
  }
];
