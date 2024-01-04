import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable()
export class ErrorHandlingInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler,
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMsg = '';
        if (error.status >= 400 && error.status < 500) {
          errorMsg = error.error.message;
        } else {
          errorMsg = 'Wystąpił błąd. Spróbuj ponownie';
        }
        return throwError(errorMsg);
      }),
    );
  }
}
