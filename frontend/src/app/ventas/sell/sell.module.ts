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
import { SellVariantselectionComponent } from './sell-variantselection.component';
import { SellLimitStockErrorComponent } from "./sell-limit-stock-error.component";
import { SellNotificationErrorComponent } from "./sell-notification-error.component";
import { SellExtraInfoComponent } from "./sell-extra-info.component";
import {SellShowImageComponent} from "./sell-show-image.component";
import {SatDatepickerModule, SatNativeDateModule} from "saturn-datepicker";
import {MatMomentDateModule} from "@angular/material-moment-adapter";

@NgModule({
  imports: [
    CommonModule,
    DemoMaterialModule,
    FlexLayoutModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule,
    SatDatepickerModule, SatNativeDateModule,
    RouterModule.forChild(SellRoutes)
  ],
  entryComponents: [SellDeleteComponent, SellVariantselectionComponent, SellLimitStockErrorComponent, SellExtraInfoComponent, SellNotificationErrorComponent, SellShowImageComponent],
  declarations: [SellComponent, SellDetailComponent, SellUpdateComponent,
    SellDeleteComponent, SellVariantselectionComponent, SellLimitStockErrorComponent, SellExtraInfoComponent, SellNotificationErrorComponent, SellShowImageComponent]

})
export class SellModule {}
