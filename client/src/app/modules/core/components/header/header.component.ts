import { Component } from '@angular/core';
import { AppState } from '../../../../store/app.reducer';
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  constructor(private store: Store<AppState>) {}
  logout() {}
}
