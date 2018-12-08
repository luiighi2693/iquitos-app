import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { UserComponent } from './user.component';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { UserDetailComponent } from './user-detail.component';
import { UserUpdateComponent } from './user-update.component';
import {UsuarioExterno, IUsuarioExterno} from "../../models/usuario-externo.model";
import {UsuarioExternoService} from "./usuario-externo.service";

@Injectable({ providedIn: 'root' })
export class UsuarioResolve implements Resolve<IUsuarioExterno> {
  constructor(private service: UsuarioExternoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UsuarioExterno> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UsuarioExterno>) => response.ok),
        map((entity: HttpResponse<UsuarioExterno>) => entity.body)
      );
    }
    return of(new UsuarioExterno());
  }
}

export const UserRoutes: Routes = [
  {
    path: '',
    component: UserComponent
  },
  {
    path: ':id/view',
    component: UserDetailComponent,
    resolve: {
      entity: UsuarioResolve
    }
  },
  {
    path: 'new',
    component: UserUpdateComponent,
    resolve: {
      entity: UsuarioResolve
    }
  },
  {
    path: ':id/edit',
    component: UserUpdateComponent,
    resolve: {
      entity: UsuarioResolve
    }
  }
];
