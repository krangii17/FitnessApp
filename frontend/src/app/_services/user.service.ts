import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {User} from 'src/app/_models/user';

@Injectable({providedIn: 'root'})
export class UserService {
    tempUser: User;

    constructor(private http: HttpClient) {
    }

    getAll() {
        return this.http.get<User[]>(`http://localhost:8080/users`);
    }

    getById(id: number) {
        return this.http.get(`${config.apiUrl}/users/${id}`);
    }

    changePassword(user: User, email: User) {
        let body = new URLSearchParams();
        console.log(email.email + ' ' + user.password + ' ' + user.secretAnswer);
        body.set('secretAnswer', user.secretAnswer);
        body.set('email', email.email);
        body.set('password', user.password);
        let headers = new HttpHeaders({
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded',
            'No-Auth': 'True'
        });

        let options = {headers: headers};
        return this.http.post(`http://localhost:8080/changePassword`, body.toString(), options);
    }

    getAnswerByEmail(user: User) {
        return this.http.get('http://localhost:8080/getSecret?email=' + user.email);
    }

    register(user: User) {
        let body = new URLSearchParams();
        body.set('username', user.username);
        body.set('email', user.email);
        body.set('password', user.password);
        body.set('secretQuestion', user.secretQuestion);
        body.set('secretAnswer', user.secretAnswer);
        let headers = new HttpHeaders({
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded',
            'No-Auth': 'True'
        });

        let options = {headers: headers};
        return this.http.post(`http://localhost:8080/sign_up`, body.toString(),
            {headers: headers, responseType: 'text'});
    }

    update(user: User) {
        return this.http.put(`${config.apiUrl}/users/${user.id}`, user);
    }

    delete(id: number) {
        return this.http.delete(`${config.apiUrl}/users/${id}`);
    }
}
