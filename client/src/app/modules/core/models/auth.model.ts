export interface IUser {
  userName: string;
  email: string;
  role: string;
}
export class User implements IUser {
  constructor(
    public userName: string,
    public email: string,
    public role: string,
  ) {}
}

export class ActivationRequest {
  constructor(public uid: string) {}
}

export interface LoginData {
  email: string;
  password: string;
}

export interface LoginResponse {
  body: LoginResponseBody;
  errors: string;
}

export interface LoginResponseBody {
  name: string;
  surname: string;
  userName: string;
  jwtToken: string;
  refreshToken: string;
}

export interface RegisterData {
  name: string;
  surname: string;
  userName: string;
  email: string;
  password: string;
}

export interface RegisterResponse {
  timestamp: string;
  message: string;
  code: string;
}

export interface ResetPasswordData {
  email: string;
}
export interface ChangePasswordData {
  password: string;
  uid: string;
}
export interface FlashcardsListResponse {
  id: number;
  name: string;
  description: string;
  icon: string;
  isPublic: boolean;
  createdBy: string;
  createdAt: string;
}
