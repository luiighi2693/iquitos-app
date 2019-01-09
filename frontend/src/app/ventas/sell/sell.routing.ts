import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { SellComponent } from './sell.component';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { SellDetailComponent } from './sell-detail.component';
import { SellUpdateComponent } from './sell-update.component';
import {VentaService} from "./venta.service";
import {IVenta, Venta} from "../../models/venta.model";

@Injectable({ providedIn: 'root' })
export class VentaResolve implements Resolve<IVenta> {
  constructor(private service: VentaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Venta> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Venta>) => response.ok),
        map((entity: HttpResponse<Venta>) => entity.body)
      );
    }
    return of(new Venta());
  }
}

export const SellRoutes: Routes = [
  {
    path: '',
    component: SellComponent
  },
  {
    path: ':id/view',
    component: SellDetailComponent,
    resolve: {
      entity: VentaResolve
    }
  },
  {
    path: 'new',
    component: SellUpdateComponent,
    resolve: {
      entity: VentaResolve
    }
  },
  {
    path: ':id/edit',
    component: SellUpdateComponent,
    resolve: {
      entity: VentaResolve
    }
  }
];
