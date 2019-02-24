import { Routes } from '@angular/router';

import {AuthGuard} from '../shared/util/auth.guard';
import {ComprasComponent} from "./compras.component";

export const ComprasModuleRoutes: Routes = [
  {
    path: '',
    component: ComprasComponent,
    children: [
      {
        path: '',
        redirectTo: '/compras/purchase',
        pathMatch: 'full'
      },
      {
        path: 'purchase',
        canActivate: [AuthGuard],
        loadChildren: './purchase/purchase.module#PurchaseModule'
      }
    ]
  }
];
