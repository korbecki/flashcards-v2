import { Component } from '@angular/core';
import { FormService } from '../../../core/services/form.service';
import { FormControl, FormGroup } from '@angular/forms';
import { PasswordRecoveryForm } from '../../../core/models/password.recovery.form.model';

@Component({
  selector: 'app-password-recovery',
  templateUrl: './password-recovery.component.html',
  styleUrl: './password-recovery.component.scss',
})
export class PasswordRecoveryComponent {
  passwordRecoveryForm: FormGroup<PasswordRecoveryForm> =
    this.formService.initPasswordRecoveryForm();

  constructor(private formService: FormService) {}

  get controls() {
    return this.passwordRecoveryForm.controls;
  }

  getErrorMessage(control: FormControl) {
    return this.formService.getErrorMessage(control);
  }
}
