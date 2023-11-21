import { NgModule } from '@angular/core';
import { HeaderComponent } from './components/header/header.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [HeaderComponent, SharedModule],
  exports: [HeaderComponent],
})
export class CoreModule {}
