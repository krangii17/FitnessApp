import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,} from '@angular/common/http';

import {Observable} from 'rxjs';
import {AuthenticationService} from "../_services";

@Injectable()
export class HttpConfigInterceptor implements HttpInterceptor {

    constructor(public authenticationService: AuthenticationService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if (request.url == 'http://localhost:8080/sign_up') {
            console.log("without interception");
            return next.handle(request)
        }

        if (request.url.search('http://localhost:8080/getSecret') != -1) {
            console.log("without interception");
            return next.handle(request)
        }

        if (request.url.search('http://localhost:8080/changePassword') != -1) {
            console.log("without interception");
            return next.handle(request)
        }

        console.log("in interception");

        request = request.clone({
            headers: request.headers.set('Authorization', 'Basic '
                + btoa(this.authenticationService.username + ':' + this.authenticationService.password))
        });

        if (!request.headers.has('Content-Type')) {
            request = request.clone({headers: request.headers.set('Content-Type', 'application/json')});
        }

        request = request.clone({headers: request.headers.set('Accept', 'application/json')});

        return next.handle(request)

    }
}
