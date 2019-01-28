import { Routes } from '@angular/router';
import {Invoice2Component} from "./invoice/invoice.2component";

export const Pages2Routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'invoice2/:id/:index',
        component: Invoice2Component
      }
    ]
  }
];
