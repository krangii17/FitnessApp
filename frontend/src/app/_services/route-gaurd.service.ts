import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from "./authentication.service";

@Injectable({
    providedIn: 'root'
})
export class RouteGuardService implements CanActivate {

    constructor(private router: Router,
                private authS: AuthenticationService) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (!this.authS.userLoggedIn) {
            this.router.navigate(['login']);
        }

        return this.authS.userLoggedIn;
    }
}
