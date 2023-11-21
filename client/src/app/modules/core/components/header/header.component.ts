import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MaterialModule } from '../../../shared/material/material.module';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink, MaterialModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {}
