import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login';
import {WelcomeComponent} from './components/welcome';
import {ErrorComponent} from './components/error';
import {ListBooksComponent} from './components/list-books';
import {LogoutComponent} from './components/logout';
import {RouteGuardService} from './_services';
import {SignUpComponent} from './components/sign-up';
import {CoachComponent} from "./components/coach/coach.component";
import {SearchComponent} from "./components/search/search.component";
import {UserParametersComponent} from "./components/user-parameters/user-parameters";
import { RecoveryComponent } from './components';
import {AdminPageComponent} from './components/admin-page';


const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'welcome/:name', component: WelcomeComponent, canActivate: [RouteGuardService]},
  {path: 'books', component: ListBooksComponent, canActivate: [RouteGuardService]},
  {path: 'logout', component: LogoutComponent, canActivate: [RouteGuardService]},
  {path: 'registration', component: SignUpComponent},
    {path: 'profile', component: UserParametersComponent},
  {path: 'coach', component: SearchComponent},
  {path: 'recovery', component: RecoveryComponent},
  {path: 'admin', component: AdminPageComponent},
  {path: '**', component: ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
