import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import {DocumenttypepurchaseRoutes} from './documenttypepurchase.routing';

import { DocumenttypepurchaseComponent } from './documenttypepurchase.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {DocumenttypepurchaseDeleteComponent} from './documenttypepurchase-delete.component';

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
    RouterModule.forChild(DocumenttypepurchaseRoutes)
  ],
  entryComponents: [DocumenttypepurchaseDeleteComponent, DocumenttypepurchaseDeleteComponent],
  declarations: [DocumenttypepurchaseComponent, DocumenttypepurchaseDeleteComponent]
})
export class DocumenttypepurchaseModule {}
