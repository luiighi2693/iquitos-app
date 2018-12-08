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
      },
      {
        path: 'deliverystatus',
        canActivate: [AuthGuard],
        loadChildren: './deliverystatus/deliverystatus.module#DeliverystatusModule'
      },
      {
        path: 'documenttype',
        canActivate: [AuthGuard],
        loadChildren: './documenttype/documenttype.module#DocumenttypeModule'
      },
      {
        path: 'documenttypepurchase',
        canActivate: [AuthGuard],
        loadChildren: './documenttypepurchase/documenttypepurchase.module#DocumenttypepurchaseModule'
      },
      {
        path: 'documenttypesell',
        canActivate: [AuthGuard],
        loadChildren: './documenttypesell/documenttypesell.module#DocumenttypesellModule'
      },
      {
        path: 'paymenttype',
        canActivate: [AuthGuard],
        loadChildren: './paymenttype/paymenttype.module#PaymenttypeModule'
      },
      {
        path: 'user',
        canActivate: [AuthGuard],
        loadChildren: './user/user.module#UserModule'
      }
    ]
  }
];
