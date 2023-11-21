import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { CoreModule } from './modules/core/core.module';
import { HeaderComponent } from './modules/core/components/header/header.component';
import { AuthModule } from './modules/auth/auth.module';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    CoreModule,
    HeaderComponent,
    AuthModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'client';
}
