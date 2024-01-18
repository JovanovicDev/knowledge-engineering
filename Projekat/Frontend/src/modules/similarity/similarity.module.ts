import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Route, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { SimilarityComponent } from './similarity/similarity.component';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

const routes: Route[] = [
  { path: 'similarity', component: SimilarityComponent }
];

@NgModule({
  declarations: [
    SimilarityComponent,
    SimilarityComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    MatButtonModule,
    MatSelectModule,
    MatFormFieldModule,
    RouterModule.forRoot(routes)
  ]
})
export class SimilarityModule { }
