import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginForm } from '../models/login.form.model';
import { RegisterForm } from '../models/register.form.model';

@Injectable({
  providedIn: 'root',
})
export class FormService {
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
    return new FormGroup({
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
    });
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
    }

    return '';
  }
}
