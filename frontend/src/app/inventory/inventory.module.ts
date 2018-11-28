import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryComponent } from './inventory.component';
import {RouterModule} from "@angular/router";
import {ContactModuleRoutes} from "./inventory.routing";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(ContactModuleRoutes)
  ],
  declarations: [InventoryComponent]
})
export class InventoryModule { }
