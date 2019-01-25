import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SystemParamRoutes } from './systemparam.routing';

import { SystemparamComponent } from './systemparam.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {SystemparamDeleteComponent} from './systemparam-delete.component';

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
    RouterModule.forChild(SystemParamRoutes)
  ],
  entryComponents: [SystemparamDeleteComponent, SystemparamDeleteComponent],
  declarations: [SystemparamComponent, SystemparamDeleteComponent]
})
export class SystemparamModule {}
