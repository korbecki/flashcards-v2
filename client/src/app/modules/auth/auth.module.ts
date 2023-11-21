import { NgModule } from '@angular/core';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [],
  imports: [SharedModule, AuthRoutingModule, LoginComponent, RegisterComponent],
  exports: [LoginComponent, RegisterComponent],
})
export class AuthModule {}
