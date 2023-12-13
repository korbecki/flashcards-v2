import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginForm } from '../models/login.form.model';
import { RegisterForm } from '../models/register.form.model';
import { PasswordRecoveryForm } from '../models/password.recovery.form.model';
import { PasswordsForm } from '../models/passwords.form.model';
import { equivalentValidator } from '../../shared/validators/equivalent.validator';

@Injectable({
  providedIn: 'root',
})
export class FormService {
  initPasswordRecoveryForm(): FormGroup<PasswordRecoveryForm> {
    return new FormGroup({
      email: new FormControl('', {
        validators: [Validators.required, Validators.email],
        nonNullable: true,
      }),
    });
  }

  initPasswordsForm(): FormGroup<PasswordsForm> {
    return new FormGroup(
      {
        password: new FormControl('', {
          validators: [Validators.required, Validators.minLength(4)],
          nonNullable: true,
        }),
        retypePassword: new FormControl('', {
          validators: [Validators.required],
          nonNullable: true,
        }),
      },
      { validators: [equivalentValidator('password', 'retypePassword')] },
    );
  }

  initLoginForm(): FormGroup<LoginForm> {
    return new FormGroup({
      email: new FormControl('', {
        validators: [Validators.required, Validators.email],
        nonNullable: true,
      }),
      password: new FormControl('', {
        validators: [Validators.required],
        nonNullable: true,
      }),
    });
  }

  initRegisterForm(): FormGroup<RegisterForm> {
    return new FormGroup(
      {
        name: new FormControl('', {
          validators: [
            Validators.required,
            Validators.minLength(2),
            Validators.maxLength(50),
          ],
          nonNullable: true,
        }),
        surname: new FormControl('', {
          validators: [
            Validators.required,
            Validators.minLength(2),
            Validators.maxLength(50),
          ],
          nonNullable: true,
        }),
        userName: new FormControl('', {
          validators: [
            Validators.required,
            Validators.minLength(4),
            Validators.maxLength(50),
          ],
          nonNullable: true,
        }),
        email: new FormControl('', {
          validators: [Validators.required, Validators.email],
          nonNullable: true,
        }),
        password: new FormControl('', {
          validators: [Validators.required, Validators.minLength(4)],
          nonNullable: true,
        }),
        retypePassword: new FormControl('', {
          validators: [Validators.required],
          nonNullable: true,
        }),
      },
      { validators: [equivalentValidator('password', 'retypePassword')] },
    );
  }

  getErrorMessage(control: FormControl): string {
    if (control.hasError('required')) {
      return 'The field cannot be empty';
    } else if (control.hasError('email')) {
      return 'Enter valid e-mail';
    } else if (control.hasError('minlength')) {
      return `The field must be at least ${control.errors?.['minlength']?.requiredLength} character`;
    } else if (control.hasError('maxlength')) {
      return `The field must be longer than ${control.errors?.['maxlength']?.requiredLength} character`;
    } else if (control.hasError('valueNotEqual')) {
      return `The passwords are not the same`;
    }

    return '';
  }
}
