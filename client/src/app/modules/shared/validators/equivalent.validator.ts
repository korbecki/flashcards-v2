import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const equivalentValidator = (
  firstValue: string,
  secondValue: string,
): ValidatorFn => {
  return (control: AbstractControl): ValidationErrors | null => {
    const firstControl = control.get(firstValue);
    const secondControl = control.get(secondValue);

    if (firstControl?.value && secondControl?.value !== firstControl?.value) {
      secondControl?.setErrors({
        valueNotEqual: true,
      });
    }

    return null;
  };
};
