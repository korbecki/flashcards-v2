import { Component } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormControl, Validators } from '@angular/forms';
import { UserLoginModel } from '../../core/models/login.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [SharedModule, MatFormFieldModule, MatInputModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  hide = true;
  userData: UserLoginModel = new UserLoginModel('', '');

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  passwordFormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(4),
  ]);

  getEmailErrorMessage() {
    if (this.emailFormControl.hasError('required')) {
      return 'You must enter a value';
    }

    return this.emailFormControl.hasError('email') ? 'Not a valid email' : '';
  }

  getPasswordErrorMessage() {
    if (this.passwordFormControl.hasError('required')) {
      return 'You must enter a value';
    }

    return this.passwordFormControl.hasError('minLength')
      ? 'Length must be greater than 4'
      : '';
  }

  onLogin() {
    console.log(this.userData);
  }
}
