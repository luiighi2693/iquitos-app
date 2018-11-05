import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth.component';
import { FormsModule } from '@angular/forms';

import { AuthRoutingModule } from '@app/auth/auth-routing.module';

@NgModule({
  imports: [CommonModule, AuthRoutingModule, FormsModule],
  declarations: [AuthComponent]
})
export class AuthModule {}
