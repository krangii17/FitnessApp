import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {WelcomeDataService} from 'src/app/_services';

@Component({
    selector: 'app-welcome',
    templateUrl: './welcome.component.html',
    styleUrls: ['./welcome.component.css']
})

export class WelcomeComponent implements OnInit {
    name = '';

    /*
        constructor(private route: ActivatedRoute,
                    private service: WelcomeDataService
        ) {
        }
    */

    constructor(private route: ActivatedRoute) {
    }


    /*static handleSuccessfulResponse(response) {
        console.log(response);
    }

    static handleErrorResponse(response) {
        console.log(response);
    }*/

    ngOnInit() {
        //console.log(this.route.snapshot.params['name'])
        this.name = this.route.snapshot.params['name'];
        // console.log(this.name)
    }

}
