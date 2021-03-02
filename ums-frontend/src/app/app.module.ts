import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { UsersComponent } from './users/users.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LogInComponent } from './log-in/log-in.component';

@NgModule({
  // the components, directives and pipes that belong to this NgModule
  declarations: [
    AppComponent,
    UsersComponent,
    EditUserComponent,
    PageNotFoundComponent,
    LogInComponent
  ],
  // other modules whose exported classes are needed ny component templates
  // declared in this NgModule
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  // creators of services that this NgModule contributes to the global
  // collection of services; they become accessible in all parts of the app
  providers: [],
  // the main application view, called the root component,
  // which hosts all other app views
  // only the root NgModule should set the bootstrap property
  bootstrap: [AppComponent]
})
export class AppModule { }
