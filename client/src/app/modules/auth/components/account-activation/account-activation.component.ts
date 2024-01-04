import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-account-activation',
  templateUrl: './account-activation.component.html',
  styleUrl: './account-activation.component.scss',
})
export class AccountActivationComponent implements OnInit {
  errorMessage: null | string = null;
  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap((params) =>
          this.authService.activateAccount(params.get('uid') as string),
        ),
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/signing']);
        },
        error: (err) => {
          this.errorMessage = err;
        },
      });
  }
}
