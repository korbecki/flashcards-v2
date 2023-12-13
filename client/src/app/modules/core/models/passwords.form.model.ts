import { FormControl } from '@angular/forms';

export interface PasswordsForm {
  password: FormControl<string>;
  retypePassword: FormControl<string>;
}
