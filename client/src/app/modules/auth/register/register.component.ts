import { Component } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [SharedModule, MatFormFieldModule, MatInputModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  hide = true;

  email = new FormControl('', [Validators.required, Validators.email]);

  getErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }

    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  onRegister() {}
}
