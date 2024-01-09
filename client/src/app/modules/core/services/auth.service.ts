import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment.development';
import { HttpClient, HttpParams } from '@angular/common/http';
import {
  ActivationRequest,
  ChangePasswordData,
  LoginData,
  LoginResponse,
  RegisterData,
  RegisterResponse,
  ResetPasswordData,
} from '../models/auth.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  apiUrl = `${environment.apiUrl}/auth`;
  constructor(private http: HttpClient) {}

  login(body: LoginData): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, body);
  }

  logout(): Observable<RegisterResponse> {
    return this.http.get<RegisterResponse>(`${this.apiUrl}/logout`);
  }

  register(body: RegisterData): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.apiUrl}/register`, body);
  }

  activateAccount(uid: string): Observable<RegisterResponse> {
    return this.http.patch<RegisterResponse>(
      `${this.apiUrl}/register/activation`,
      new ActivationRequest(uid),
    );
  }

  resetPassword(body: ResetPasswordData): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(
      `${this.apiUrl}/reset/password`,
      body,
    );
  }

  changePassword(body: ChangePasswordData): Observable<RegisterResponse> {
    return this.http.patch<RegisterResponse>(
      `${this.apiUrl}/reset/password`,
      body,
    );
  }
}
