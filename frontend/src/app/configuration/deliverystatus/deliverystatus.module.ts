import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import {DeliverystatusRoutes} from './deliverystatus.routing';

import { DeliverystatusComponent } from './deliverystatus.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {DeliverystatusDeleteComponent} from './deliverystatus-delete.component';

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
    RouterModule.forChild(DeliverystatusRoutes)
  ],
  entryComponents: [DeliverystatusDeleteComponent, DeliverystatusDeleteComponent],
  declarations: [DeliverystatusComponent, DeliverystatusDeleteComponent]
})
export class DeliverystatusModule {}
