import { Component, OnDestroy } from '@angular/core';

import { FormControl, FormGroup } from '@angular/forms';
import { FormService } from '../../../core/services/form.service';
import { RegisterForm } from '../../../core/models/register.form.model';
import * as AuthActions from '../../store/auth.actions';
import { AppState } from '../../../../store/app.reducer';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { selectAuthError, selectAuthLoading } from '../../store/auth.selectors';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent implements OnDestroy {
  registerForm: FormGroup<RegisterForm> = this.formService.initRegisterForm();
  errorMessage$: Observable<string | null> = this.store.select(selectAuthError);
  loading$: Observable<boolean> = this.store.select(selectAuthLoading);
  constructor(
    private formService: FormService,
    private store: Store<AppState>,
  ) {}

  get controls(): RegisterForm {
    return this.registerForm.controls;
  }

  getErrorMessage(control: FormControl): string {
    return this.formService.getErrorMessage(control);
  }

  onRegister() {
    const { name, surname, userName, email, password } =
      this.registerForm.getRawValue();
    if (this.registerForm.invalid) {
      return;
    }
    this.store.dispatch(
      AuthActions.register({
        registerData: { name, surname, userName, email, password },
      }),
    );
  }
  ngOnDestroy(): void {
    this.store.dispatch(AuthActions.clearError());
  }
}
