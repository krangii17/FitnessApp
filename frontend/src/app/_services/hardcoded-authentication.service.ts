import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class HardcodedAuthenticationService {

    constructor() {
    }

    static authenticate(username, password) {
        if (username === "javainuse" && password === "password") {
            sessionStorage.setItem('username', username);
            return true;
        } else {
            return false;
        }
    }

    static isUserLoggedIn() {
        return true;
    }

    static logOut() {
        sessionStorage.removeItem('username');
    }
}
