import {Component, OnInit} from '@angular/core';
import {HardcodedAuthenticationService} from 'src/app/_services';

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

    isUserLoggedIn: boolean = false;

    constructor(private loginService: HardcodedAuthenticationService) {
    }

    get f() {
        return this.isUserLoggedIn;
    }

    ngOnInit() {
        //HardcodedAuthenticationService.isUserLoggedIn();
    }
}
