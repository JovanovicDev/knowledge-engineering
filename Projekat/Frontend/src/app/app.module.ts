import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from 'src/modules/layout/layout.module';
import { SuggestionModule } from 'src/modules/suggestion/suggestion.module';
import { ValidityModule } from 'src/modules/validity/validity.module';
import { FailureModule } from 'src/modules/failure/failure.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    SuggestionModule,
    ValidityModule,
    FailureModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
