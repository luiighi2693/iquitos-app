import { Routes } from '@angular/router';

import {AuthGuard} from '../shared/util/auth.guard';
import {VentasComponent} from "./ventas.component";

export const VentasModuleRoutes: Routes = [
  {
    path: '',
    component: VentasComponent,
    children: [
      {
        path: '',
        redirectTo: '/ventas/sell',
        pathMatch: 'full'
      },
      {
        path: 'sell',
        canActivate: [AuthGuard],
        loadChildren: './sell/sell.module#SellModule'
      },
      {
        path: 'amortizacion',
        canActivate: [AuthGuard],
        loadChildren: './amortizacion/amortizacion.module#AmortizacionModule'
      }
    ]
  }
];
