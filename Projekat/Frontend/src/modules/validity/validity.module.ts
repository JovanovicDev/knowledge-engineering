import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ValidityComponent } from './validity/validity.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  declarations: [
    ValidityComponent
  ],
  imports: [
    CommonModule,
    MatSelectModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule
  ],
  exports: [
    ValidityComponent
  ]
})
export class ValidityModule { }
