import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { VariantComponent } from './variant.component';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { VariantDetailComponent } from './variant-detail.component';
import { VariantUpdateComponent } from './variant-update.component';
import {VarianteService} from "./variante.service";
import {IVariante, Variante} from "../../models/variante.model";

@Injectable({ providedIn: 'root' })
export class VarianteResolve implements Resolve<IVariante> {
  constructor(private service: VarianteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Variante> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Variante>) => response.ok),
        map((entity: HttpResponse<Variante>) => entity.body)
      );
    }
    return of(new Variante());
  }
}

export const VariantRoutes: Routes = [
  {
    path: '',
    component: VariantComponent
  },
  {
    path: ':id/view',
    component: VariantDetailComponent,
    resolve: {
      entity: VarianteResolve
    }
  },
  {
    path: 'new',
    component: VariantUpdateComponent,
    resolve: {
      entity: VarianteResolve
    }
  },
  {
    path: ':id/edit',
    component: VariantUpdateComponent,
    resolve: {
      entity: VarianteResolve
    }
  }
];
