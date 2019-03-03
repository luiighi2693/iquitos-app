import { Routes } from '@angular/router';
import {Invoice2Component} from "./invoice/invoice.2component";
import {InvoiceA5Component} from "./invoiceA5/invoiceA5.component";

export const Pages2Routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'invoice2/:id/:index',
        component: Invoice2Component
      },
      {
        path: 'invoicea5/:id/:index',
        component: InvoiceA5Component
      }
    ]
  }
];
