import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { RegisterForm } from '../../../core/models/register.form.model';
import { FormService } from '../../../core/services/form.service';
import { PasswordsForm } from '../../../core/models/passwords.form.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-password-recovery-form',
  templateUrl: './password-recovery-form.component.html',
  styleUrl: './password-recovery-form.component.scss',
})
export class PasswordRecoveryFormComponent implements OnInit {
  passwordsForm: FormGroup<PasswordsForm> =
    this.formService.initPasswordsForm();
  constructor(
    private formService: FormService,
    private route: ActivatedRoute,
  ) {}

  get controls(): PasswordsForm {
    return this.passwordsForm.controls;
  }

  getErrorMessage(control: FormControl): string {
    return this.formService.getErrorMessage(control);
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: (param) => {
        console.log(param.get('uid'));
      },
    });
  }
}
