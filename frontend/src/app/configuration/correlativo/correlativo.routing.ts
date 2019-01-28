import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';

import { CorrelativoComponent } from './correlativo.component';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

// @Injectable({ providedIn: 'root' })
// export class UsuarioResolve implements Resolve<IUsuarioExterno> {
//   constructor(private service: UsuarioExternoService) {}
//
//   resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UsuarioExterno> {
//     const id = route.params['id'] ? route.params['id'] : null;
//     if (id) {
//       return this.service.find(id).pipe(
//         filter((response: HttpResponse<UsuarioExterno>) => response.ok),
//         map((entity: HttpResponse<UsuarioExterno>) => entity.body)
//       );
//     }
//     return of(new UsuarioExterno());
//   }
// }

export const CorrelativoRoutes: Routes = [
  {
    path: '',
    component: CorrelativoComponent
  }
];
