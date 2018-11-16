import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { ProviderComponent } from './provider.component';
import { Injectable } from '@angular/core';
import { IProveedor, Proveedor } from '../models/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { ProviderDetailComponent } from './provider-detail.component';
import { ProviderUpdateComponent } from './provider-update.component';

declare var require: any;

@Injectable({ providedIn: 'root' })
export class ProveedorResolve implements Resolve<IProveedor> {
  dataIn: any = require('./data.json');

  constructor(private service: ProveedorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Proveedor {
    const id = route.params['id'] ? route.params['id'] : null;
    return this.dataIn.find(x => x.id === id);
    // if (id) {
      // return this.service.find(id).pipe(
      //   filter((response: HttpResponse<Proveedor>) => response.ok),
      //   map((proveedor: HttpResponse<Proveedor>) => proveedor.body)
      // );
    // }
    // return of(new Proveedor());
  }
}

export const ProviderRoutes: Routes = [
  {
    path: '',
    component: ProviderComponent
  },
  {
    path: ':id/view',
    component: ProviderDetailComponent,
    resolve: {
      proveedor: ProveedorResolve
    }
  },
  {
    path: 'new',
    component: ProviderUpdateComponent,
    resolve: {
      proveedor: ProveedorResolve
    }
  },
  {
    path: ':id/edit',
    component: ProviderUpdateComponent,
    resolve: {
      proveedor: ProveedorResolve
    }
  }
];
