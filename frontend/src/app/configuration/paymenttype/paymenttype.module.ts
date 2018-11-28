import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import {PaymenttypeRoutes} from './paymenttype.routing';

import { PaymenttypeComponent } from './paymenttype.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {PaymenttypeDeleteComponent} from './paymenttype-delete.component';

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
    RouterModule.forChild(PaymenttypeRoutes)
  ],
  entryComponents: [PaymenttypeDeleteComponent, PaymenttypeDeleteComponent],
  declarations: [PaymenttypeComponent, PaymenttypeDeleteComponent]
})
export class PaymenttypeModule {}
