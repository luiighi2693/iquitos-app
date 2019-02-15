import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import {DocumenttypesellRoutes} from './documenttypesell.routing';

import { DocumenttypesellComponent } from './documenttypesell.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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
    RouterModule.forChild(DocumenttypesellRoutes)
  ],
  entryComponents: [],
  declarations: [DocumenttypesellComponent]
})
export class DocumenttypesellModule {}
