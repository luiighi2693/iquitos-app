import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthComponent } from './auth.component';

const routes: Routes = [
  {
    path: '',
    component: AuthComponent,
    children: [
      { path: 'login', loadChildren: './modules/login/login.module#LoginModule' },
      // { path: 'ejemplo', loadChildren: '../ejemplo/ejemplo.module#EjemploModule' },
      // { path: 'producto',
      //   loadChildren: '../productoDetalle/producto.detalle.module#ProductoDetalleModule' },
      { path: '**', pathMatch: 'full', redirectTo: 'login' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {}
