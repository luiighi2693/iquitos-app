import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VentasComponent } from './ventas.component';
import {RouterModule} from "@angular/router";
import {VentasModuleRoutes} from "./ventas.routing";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(VentasModuleRoutes)
  ],
  declarations: [VentasComponent]
})
export class VentasModule { }
