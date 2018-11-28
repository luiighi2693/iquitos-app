import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { PurchasestatusRoutes } from './purchasestatus.routing';

import { PurchasestatusComponent } from './purchasestatus.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {PurchasestatusDeleteComponent} from './purchasestatus-delete.component';

import { NgxDatatableModule } from '@swimlane/ngx-datatable';


@NgModule({
  imports: [
    CommonModule,
    DemoMaterialModule,
    FlexLayoutModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule,
    NgxDatatableModule,
    RouterModule.forChild(PurchasestatusRoutes)
  ],
  entryComponents: [PurchasestatusDeleteComponent, PurchasestatusDeleteComponent],
  declarations: [PurchasestatusComponent, PurchasestatusDeleteComponent]
})
export class PurchasestatusModule {}
