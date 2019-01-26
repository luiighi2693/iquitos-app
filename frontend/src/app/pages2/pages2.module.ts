import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../demo-material-module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Pages2Routes } from './pages2.routing';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import {Invoice2Component} from "./invoice/invoice.2component";

// import { PrintLayoutComponent } from './print-layout/print-layout.component';
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(Pages2Routes),
    DemoMaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    NgxDatatableModule
  ],
  declarations: [
    Invoice2Component,
  ]
})
export class Pages2Module {}
