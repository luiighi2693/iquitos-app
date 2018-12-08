import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../../demo-material-module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { UserRoutes } from './user.routing';

import { UserComponent } from './user.component';

import {MatSortModule} from '@angular/material/sort';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserDetailComponent } from './user-detail.component';
import { UserUpdateComponent } from './user-update.component';
import { UserDeleteComponent } from './user-delete.component';

@NgModule({
  imports: [
    CommonModule,
    DemoMaterialModule,
    FlexLayoutModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(UserRoutes)
  ],
  entryComponents: [UserDeleteComponent],
  declarations: [UserComponent, UserDetailComponent, UserUpdateComponent, UserDeleteComponent]
})
export class UserModule {}
