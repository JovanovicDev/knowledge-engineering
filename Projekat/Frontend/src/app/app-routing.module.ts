import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SuggestionComponent } from 'src/modules/suggestion/suggestion/suggestion.component';

const routes: Routes = [
  { path: 'suggestion', component: SuggestionComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
