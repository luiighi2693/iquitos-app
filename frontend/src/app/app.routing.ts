import { Routes } from '@angular/router';

import { FullComponent } from './layouts/full/full.component';
import { AppBlankComponent } from './layouts/blank/blank.component';
import {AuthGuard} from './shared/util/auth.guard';

export const AppRoutes: Routes = [
  {
    path: '',
    component: FullComponent,
    children: [
      {
        path: '',
        redirectTo: '/authentication/login',
        pathMatch: 'full'
      },
      {
        path: 'old',
        redirectTo: '/dashboards/dashboard1',
      },
      {
        path: 'dashboards',
        canActivate: [AuthGuard],
        loadChildren: './dashboards/dashboards.module#DashboardsModule'
      },
      {
        path: 'material',
        loadChildren:
          './material-component/material.module#MaterialComponentsModule'
      },
      {
        path: 'apps',
        loadChildren: './apps/apps.module#AppsModule'
      },
      {
        path: 'forms',
        loadChildren: './forms/forms.module#FormModule'
      },
      {
        path: 'tables',
        loadChildren: './tables/tables.module#TablesModule'
      },
      {
        path: 'datatables',
        loadChildren: './datatables/datatables.module#DataTablesModule'
      },
      {
        path: 'pages',
        loadChildren: './pages/pages.module#PagesModule'
      },
      {
        path: 'widgets',
        loadChildren: './widgets/widgets.module#WidgetsModule'
      },
      {
        path: 'charts',
        loadChildren: './charts/chartslib.module#ChartslibModule'
      },
      {
        path: 'multi',
        loadChildren: './multi-dropdown/multi-dd.module#MultiModule'
      },
      {
        path: 'configuration',
        canActivate: [AuthGuard],
        loadChildren: './configuration/configuration.module#ConfigurationModule'
      },
      {
        path: 'contact',
        canActivate: [AuthGuard],
        loadChildren: './contact/contact.module#ContactModule'
      },
      {
        path: 'inventory',
        canActivate: [AuthGuard],
        loadChildren: './inventory/inventory.module#InventoryModule'
      },
      {
        path: 'ventas',
        canActivate: [AuthGuard],
        loadChildren: './ventas/ventas.module#VentasModule'
      }
    ]
  },
  {
    path: '',
    component: AppBlankComponent,
    children: [
      {
        path: 'authentication',
        loadChildren:
          './authentication/authentication.module#AuthenticationModule'
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'authentication/404'
  }
];
