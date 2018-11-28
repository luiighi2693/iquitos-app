import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import {DocumenttypeRoutes} from './documenttype.routing';

import { DocumenttypeComponent } from './documenttype.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {DocumenttypeDeleteComponent} from './documenttype-delete.component';

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
    RouterModule.forChild(DocumenttypeRoutes)
  ],
  entryComponents: [DocumenttypeDeleteComponent, DocumenttypeDeleteComponent],
  declarations: [DocumenttypeComponent, DocumenttypeDeleteComponent]
})
export class DocumenttypeModule {}
