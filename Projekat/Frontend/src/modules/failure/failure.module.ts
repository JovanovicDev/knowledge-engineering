import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FailureComponent } from './failure/failure.component';



@NgModule({
  declarations: [
    FailureComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    FailureComponent
  ]
})
export class FailureModule { }
