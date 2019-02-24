import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ComprasComponent } from './compras.component';
import {RouterModule} from "@angular/router";
import {ComprasModuleRoutes} from "./compras.routing";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(ComprasModuleRoutes)
  ],
  declarations: [ComprasComponent]
})
export class ComprasModule { }
