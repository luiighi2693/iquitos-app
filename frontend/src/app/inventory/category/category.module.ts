import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import {CategoryRoutes} from './category.routing';

import { CategoryComponent } from './category.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {CategoryDeleteComponent} from './category-delete.component';

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
    RouterModule.forChild(CategoryRoutes)
  ],
  entryComponents: [CategoryDeleteComponent, CategoryDeleteComponent],
  declarations: [CategoryComponent, CategoryDeleteComponent]
})
export class CategoryModule {}
