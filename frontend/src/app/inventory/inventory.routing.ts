import { Routes } from '@angular/router';

import {AuthGuard} from '../shared/util/auth.guard';
import {InventoryComponent} from "./inventory.component";

export const ContactModuleRoutes: Routes = [
  {
    path: '',
    component: InventoryComponent,
    children: [
      {
        path: '',
        redirectTo: '/inventory/category',
        pathMatch: 'full'
      },
      {
        path: 'category',
        canActivate: [AuthGuard],
        loadChildren: './category/category.module#CategoryModule'
      },
      {
        path: 'variant',
        canActivate: [AuthGuard],
        loadChildren: './variant/variant.module#VariantModule'
      },
      {
        path: 'product',
        canActivate: [AuthGuard],
        loadChildren: './product/product.module#ProductModule'
      }
    ]
  }
];
