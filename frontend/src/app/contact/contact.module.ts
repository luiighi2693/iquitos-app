import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContactComponent } from './contact.component';
import {RouterModule} from "@angular/router";
import {ContactModuleRoutes} from "./contact.routing";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(ContactModuleRoutes)
  ],
  declarations: [ContactComponent]
})
export class ContactModule { }
