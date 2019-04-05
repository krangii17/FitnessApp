import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class CoachService {
    constructor(private http: HttpClient) {
    }

    getUsers() {
        return this.http.get(`https://randomuser.me/api/?results=9`)
            .pipe(map(response => {
                return response['results'];
            }))
            .pipe(map(users => {
                return users.map(u => {
                    return {
                        name: u.name.first + ' ' + u.name.last,
                        image: u.picture.large,
                        phone: u.phone,
                        email: u.email
                    };
                });
            }));
    }
}
