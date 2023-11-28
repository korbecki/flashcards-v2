import { NgModule } from '@angular/core';
import { HeaderComponent } from './components/header/header.component';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [HeaderComponent],
  imports: [RouterLinkActive, RouterLink, SharedModule],
  exports: [HeaderComponent],
})
export class CoreModule {}
