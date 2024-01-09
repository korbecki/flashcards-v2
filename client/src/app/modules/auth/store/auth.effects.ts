import { Injectable } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as AuthActions from './auth.actions';
import { catchError, map, of, switchMap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class AuthEffects {
  login$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthActions.login),
      switchMap((action) => {
        return this.authService.login(action.loginData).pipe(
          map((user) => {
            localStorage.setItem('JWT_TOKEN', user.body.jwtToken);
            this.router.navigate(['/home']);
            return AuthActions.loginSuccess({ user: { ...user } });
          }),
          catchError(() =>
            of(AuthActions.loginFailure({ error: 'Wystapił błąd.' })),
          ),
        );
      }),
    );
  });

  register$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthActions.register),
      switchMap((action) => {
        return this.authService.register(action.registerData).pipe(
          map(() => {
            this.router.navigate(['/signing']);
            return AuthActions.registerSuccess();
          }),
          catchError((err) => {
            console.log(err);
            return of(AuthActions.loginFailure({ error: 'Wystapił błąd.' }));
          }),
        );
      }),
    );
  });

  logout$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthActions.logout),
      switchMap(() => {
        return this.authService.logout().pipe(
          map(() => {
            this.router.navigate(['/signing']);
            return AuthActions.logoutSuccess;
          }),
          catchError(() => of(AuthActions.logoutFailure())),
        );
      }),
    );
  });

  constructor(
    private actions$: Actions,
    private authService: AuthService,
    private router: Router,
  ) {}
}
