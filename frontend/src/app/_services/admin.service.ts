import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../_models';

@Injectable({
    providedIn: 'root'
})

export class AdminService {
    // TODO paste URL what we will use in real program
    private Url = '';

    constructor(private httpClient: HttpClient) {
    }

    getAllUsers() {
        return this.httpClient.get(`${this.Url}`);
    }

    getUserById(id: number) {
        return this.httpClient.get(`${this.Url}/${id}`);
    }

    createUser(user: User) {
        return this.httpClient.post(`${this.Url}`, user);
    }

    editUser(id: number, user: User) {
        return this.httpClient.put(`${this.Url}`, user);
    }

    deleteUserById(id: number) {
        this.httpClient.delete(`${this.Url}/${id}`);
    }
}
