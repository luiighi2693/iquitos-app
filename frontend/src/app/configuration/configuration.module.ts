import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigurationComponent } from './configuration.component';
import {RouterModule} from "@angular/router";
import {ConfigurationRoutes} from "./configuration.routing";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(ConfigurationRoutes)
  ],
  declarations: [ConfigurationComponent]
})
export class ConfigurationModule { }
