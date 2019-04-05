import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {WelcomeComponent} from './components/welcome';
import {LoginComponent} from './components/login';
import {ErrorComponent} from './components/error';
import {ListBooksComponent} from './components/list-books';
import {MenuComponent} from './components/menu';
import {FooterComponent} from './components/footer';
import {LogoutComponent} from './components/logout';
import {SignUpComponent} from './components/sign-up';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CoachComponent} from './components/coach/coach.component';
import {SearchComponent} from './components/search/search.component';
import {SearchPipe} from './search.pipe';
import {UserParametersComponent} from "./components/user-parameters/user-parameters";
import {RecoveryComponent} from './components/recovery/recovery.component';
import {AdminPageComponent} from './components/admin-page/admin-page.component';
import {UserFilterPipe} from './_pipes/user-filter.pipe';
import {AuthenticationService} from "./_services";
import {HttpConfigInterceptor} from "./_interceptor/httpconfig.interceptor";


@NgModule({
    declarations: [
        SearchPipe,
        AppComponent,
        WelcomeComponent,
        LoginComponent,
        ErrorComponent,
        ListBooksComponent,
        MenuComponent,
        FooterComponent,
        LogoutComponent,
        SignUpComponent,
        AdminPageComponent,
        UserFilterPipe,
        CoachComponent,
        SearchComponent,
        RecoveryComponent,
        UserParametersComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    providers: [
        AuthenticationService,
        {provide: HTTP_INTERCEPTORS, useClass: HttpConfigInterceptor, multi: true}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
