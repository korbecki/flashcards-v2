import { FormControl } from '@angular/forms';

export interface RegisterForm {
  name: FormControl<string>;
  surname: FormControl<string>;
  userName: FormControl<string>;
  email: FormControl<string>;
  password: FormControl<string>;
  retypePassword: FormControl<string>;
}
