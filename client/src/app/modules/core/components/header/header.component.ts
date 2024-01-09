import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  @Input() title: string = '';
  @Input() showSearch: boolean = false;
  @Input() showLogout: boolean = false;

  constructor(private router: Router) {}

  logout() {
    // Tutaj dodaj logikę wylogowania (np. usuń token sesji, przekieruj do strony logowania)
    // ...

    // Przekieruj do strony logowania
    this.router.navigate(['/login']);
  }
}
