import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { AmortizationRoutes } from './amortizacion.routing';

import { AmortizacionComponent } from './amortizacion.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AmortizacionExtraInfoComponent } from "./amortizacion-extra-info.component";

@NgModule({
  imports: [
    CommonModule,
    DemoMaterialModule,
    FlexLayoutModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(AmortizationRoutes)
  ],
  entryComponents: [AmortizacionExtraInfoComponent],
  declarations: [AmortizacionComponent, AmortizacionExtraInfoComponent]
})
export class AmortizacionModule {}
