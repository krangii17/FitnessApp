import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

export class Employee {
    constructor(
        public empId: string,
        public name: string,
        public designation: string,
        public salary: string,
    ) {
    }
}

@Injectable({
    providedIn: 'root'
})
export class WelcomeDataService {

    constructor(
        private http: HttpClient
    ) {
    }

    executeHelloWorld() {
        console.log("test call");
        return this.http.get<Employee>('http://localhost:8080/employee');
    }
}
