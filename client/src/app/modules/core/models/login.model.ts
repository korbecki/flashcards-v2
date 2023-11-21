export class UserLoginModel {
  private _email: string;
  private _password: string;

  constructor(username: string, password: string) {
    this._email = username;
    this._password = password;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }
}
