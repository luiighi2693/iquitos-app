import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { SystemparamComponent } from './systemparam.component';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import {IParametroSistema, ParametroSistema} from "../../models/parametro-sistema.model";
import {ParametroSistemaService} from "./parametro-sistema.service";

@Injectable({ providedIn: 'root' })
export class RelatedproductstagResolve implements Resolve<IParametroSistema> {
  constructor(private service: ParametroSistemaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ParametroSistema> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ParametroSistema>) => response.ok),
        map((proveedor: HttpResponse<ParametroSistema>) => proveedor.body)
      );
    }
    return of(new ParametroSistema());
  }
}

export const SystemParamRoutes: Routes = [
  {
    path: '',
    component: SystemparamComponent
  }
];
