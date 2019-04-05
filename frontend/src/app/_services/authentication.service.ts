import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

import {User} from 'src/app/_models';

@Injectable({providedIn: 'root'})
export class AuthenticationService {

    public currentUser: Observable<User>;
    private currentUserSubject: BehaviorSubject<User>;

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
        this._userLoggedIn = false;
    }

    private _username: String;

    get username(): String {
        return this._username;
    }

    set username(value: String) {
        this._username = value;
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    private _password: String;

    get password(): String {
        return this._password;
    }

    set password(value: String) {
        this._password = value;
    }

    private _userLoggedIn: boolean;

    get userLoggedIn(): boolean {
        return this._userLoggedIn;
    }

    set userLoggedIn(value: boolean) {
        this._userLoggedIn = value;
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }

    login() {
        return this.http.get(`http://localhost:8080/login`, {responseType: 'text'});
    }
}
