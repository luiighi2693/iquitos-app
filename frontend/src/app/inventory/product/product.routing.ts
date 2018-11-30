import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { ProductComponent } from './product.component';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { ProductDetailComponent } from './product-detail.component';
import { ProductUpdateComponent } from './product-update.component';
import {ProductoService} from "./producto.service";
import {IProducto, Producto} from "../../models/producto.model";

@Injectable({ providedIn: 'root' })
export class ProductoResolve implements Resolve<IProducto> {
  constructor(private service: ProductoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Producto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Producto>) => response.ok),
        map((entity: HttpResponse<Producto>) => entity.body)
      );
    }
    return of(new Producto());
  }
}

export const ProductRoutes: Routes = [
  {
    path: '',
    component: ProductComponent
  },
  {
    path: ':id/view',
    component: ProductDetailComponent,
    resolve: {
      entity: ProductoResolve
    }
  },
  {
    path: 'new',
    component: ProductUpdateComponent,
    resolve: {
      entity: ProductoResolve
    }
  },
  {
    path: ':id/edit',
    component: ProductUpdateComponent,
    resolve: {
      entity: ProductoResolve
    }
  }
];
