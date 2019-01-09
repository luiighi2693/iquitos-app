import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SellRoutes } from './sell.routing';

import { SellComponent } from './sell.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SellDetailComponent } from './sell-detail.component';
import { SellUpdateComponent } from './sell-update.component';
import { SellDeleteComponent } from './sell-delete.component';

@NgModule({
  imports: [
    CommonModule,
    DemoMaterialModule,
    FlexLayoutModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(SellRoutes)
  ],
  entryComponents: [SellDeleteComponent],
  declarations: [SellComponent, SellDetailComponent, SellUpdateComponent, SellDeleteComponent]
})
export class SellModule {}
