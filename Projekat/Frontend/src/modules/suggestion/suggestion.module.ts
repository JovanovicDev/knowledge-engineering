import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SuggestionComponent } from './suggestion/suggestion.component';
import { MatSelectModule } from '@angular/material/select';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatButtonModule } from "@angular/material/button";
import { MatCheckboxModule } from '@angular/material/checkbox';
import { UpgradeComponent } from './upgrade/upgrade.component'; 

@NgModule({
  declarations: [
    SuggestionComponent,
    UpgradeComponent
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
    SuggestionComponent,
  ]
})
export class SuggestionModule { }
