import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {UserParameters} from "../_models/userParameters";
import {Restriction} from "../_models/restriction";


@Injectable()
export class UserParametersService {

    constructor(private http: HttpClient) {
    }

    getUserParameters() {
        return this.http.get(`http://localhost:8080/profiles/auth-user`);
    }

    getRestrictions() {
        return this.http.get('/assets/restrictions.json');
    }


    setUserParameters(userParametersToSet: UserParameters) {
        return this.http.post('http://localhost:8080/profiles/auth-user', userParametersToSet, {responseType: 'text'});
    }

    setRestrictions(restrictionsToSet: Restriction[]) {
        return this.http.post('/assets/restrictions.json', restrictionsToSet, {responseType: 'text'});
    }

}
