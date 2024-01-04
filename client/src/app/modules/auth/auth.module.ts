import { NgModule } from '@angular/core';

import { AuthRoutingModule } from './auth-routing.module';
import { SharedModule } from '../shared/shared.module';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AccountActivationComponent } from './components/account-activation/account-activation.component';
import { PasswordRecoveryComponent } from './components/password-recovery/password-recovery.component';
import { PasswordRecoveryFormComponent } from './components/password-recovery-form/password-recovery-form.component';
import { BackgroundComponent } from './components/background/background.component';

@NgModule({
  declarations: [
    LoginComponent,
    BackgroundComponent,
    RegisterComponent,
    AccountActivationComponent,
    PasswordRecoveryComponent,
    PasswordRecoveryFormComponent,
  ],
  imports: [SharedModule, AuthRoutingModule],
})
export class AuthModule {}
