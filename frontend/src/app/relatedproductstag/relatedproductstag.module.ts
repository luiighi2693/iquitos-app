import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { RelatedproductstagRoutes } from './relatedproductstag.routing';

import { RelatedproductstagComponent } from './relatedproductstag.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {RelatedproductstagDeleteComponent} from './relatedproductstag-delete.component';

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
    RouterModule.forChild(RelatedproductstagRoutes)
  ],
  entryComponents: [RelatedproductstagDeleteComponent, RelatedproductstagDeleteComponent],
  declarations: [RelatedproductstagComponent, RelatedproductstagDeleteComponent]
})
export class RelatedproductstagModule {}
