import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { PurchaseRoutes } from './purchase.routing';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PurchaseUpdateComponent } from './purchase-update.component';
import {PurchaseComponent} from "./purchase.component";

@NgModule({
  imports: [
    CommonModule,
    DemoMaterialModule,
    FlexLayoutModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(PurchaseRoutes)
  ],
  entryComponents: [],
  declarations: [PurchaseComponent, PurchaseUpdateComponent]
})
export class PurchaseModule {}
