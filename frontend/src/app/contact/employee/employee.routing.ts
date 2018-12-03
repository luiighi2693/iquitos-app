import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { EmployeeComponent } from './employee.component';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { EmployeeDetailComponent } from './employee-detail.component';
import { EmployeeUpdateComponent } from './employee-update.component';
import {EmpleadoService} from "./empleado.service";
import {Empleado, IEmpleado} from "../../models/empleado.model";

@Injectable({ providedIn: 'root' })
export class EmpleadoResolve implements Resolve<IEmpleado> {
  constructor(private service: EmpleadoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Empleado> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Empleado>) => response.ok),
        map((entity: HttpResponse<Empleado>) => entity.body)
      );
    }
    return of(new Empleado());
  }
}

export const EmployeeRoutes: Routes = [
  {
    path: '',
    component: EmployeeComponent
  },
  {
    path: ':id/view',
    component: EmployeeDetailComponent,
    resolve: {
      entity: EmpleadoResolve
    }
  },
  {
    path: 'new',
    component: EmployeeUpdateComponent,
    resolve: {
      entity: EmpleadoResolve
    }
  },
  {
    path: ':id/edit',
    component: EmployeeUpdateComponent,
    resolve: {
      entity: EmpleadoResolve
    }
  }
];
