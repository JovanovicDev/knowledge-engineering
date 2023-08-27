import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ValidityComponent } from './validity/validity.component';



@NgModule({
  declarations: [
    ValidityComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ValidityComponent
  ]
})
export class ValidityModule { }
