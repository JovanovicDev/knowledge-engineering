import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FailureComponent } from 'src/modules/failure/failure/failure.component';
import { SuggestionComponent } from 'src/modules/suggestion/suggestion/suggestion.component';
import { ValidityComponent } from 'src/modules/validity/validity/validity.component';

const routes: Routes = [
  { path: 'suggestion', component: SuggestionComponent },
  { path: 'validity', component: ValidityComponent },
  { path: 'failure', component: FailureComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
