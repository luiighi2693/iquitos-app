import { Routes } from '@angular/router';

import {AuthGuard} from '../shared/util/auth.guard';
import {ConfigurationComponent} from "./configuration.component";

export const ConfigurationRoutes: Routes = [
  {
    path: '',
    component: ConfigurationComponent,
    children: [
      {
        path: '',
        redirectTo: '/configuration/relatedproducts',
        pathMatch: 'full'
      },
      {
        path: 'relatedproducts',
        canActivate: [AuthGuard],
        loadChildren: './relatedproductstag/relatedproductstag.module#RelatedproductstagModule'
      },
      {
        path: 'purchasestatus',
        canActivate: [AuthGuard],
        loadChildren: './purchasestatus/purchasestatus.module#PurchasestatusModule'
      }
    ]
  }
];
