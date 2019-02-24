import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { PurchaseComponent } from './purchase.component';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { PurchaseUpdateComponent } from './purchase-update.component';
import {Compra, ICompra} from "../../models/compra.model";
import {CompraService} from "./compra.service";

@Injectable({ providedIn: 'root' })
export class CompraResolve implements Resolve<ICompra> {
  constructor(private service: CompraService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Compra> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Compra>) => response.ok),
        map((entity: HttpResponse<Compra>) => entity.body)
      );
    }
    return of(new Compra());
  }
}

export const PurchaseRoutes: Routes = [
  {
    path: '',
    component: PurchaseComponent
  },
  {
    path: 'new',
    component: PurchaseUpdateComponent,
    resolve: {
      entity: CompraResolve
    }
  },
  {
    path: ':id/edit',
    component: PurchaseUpdateComponent,
    resolve: {
      entity: CompraResolve
    }
  }
];
