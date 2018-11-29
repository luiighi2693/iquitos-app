import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { VariantRoutes } from './variant.routing';

import { VariantComponent } from './variant.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { VariantDetailComponent } from './variant-detail.component';
import { VariantUpdateComponent } from './variant-update.component';
import { VariantDeleteComponent } from './variant-delete.component';

@NgModule({
  imports: [
    CommonModule,
    DemoMaterialModule,
    FlexLayoutModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(VariantRoutes)
  ],
  entryComponents: [VariantDeleteComponent],
  declarations: [VariantComponent, VariantDetailComponent, VariantUpdateComponent, VariantDeleteComponent]
})
export class VariantModule {}
