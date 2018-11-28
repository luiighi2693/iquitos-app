import { Routes } from '@angular/router';

import {AuthGuard} from '../shared/util/auth.guard';
import {ContactComponent} from "./contact.component";

export const ContactModuleRoutes: Routes = [
  {
    path: '',
    component: ContactComponent,
    children: [
      {
        path: '',
        redirectTo: '/contact/provider',
        pathMatch: 'full'
      },
      {
        path: 'provider',
        canActivate: [AuthGuard],
        loadChildren: './provider/provider.module#ProviderModule'
      }
    ]
  }
];
