import {Component, OnInit} from '@angular/core';
import {HardcodedAuthenticationService} from 'src/app/_services';

@Component({
    selector: 'app-logout',
    templateUrl: './logout.component.html',
    styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

    constructor(
        private authenticationService: HardcodedAuthenticationService) {
    }

    ngOnInit() {
        HardcodedAuthenticationService.logOut();
    }
}
