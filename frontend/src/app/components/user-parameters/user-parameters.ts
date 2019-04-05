import {Component, OnInit} from '@angular/core';
import {UserParametersService} from "../../_services/user-parameters.service";
import {UserParameters} from "../../_models/userParameters";
import {Restriction} from "../../_models/restriction";
import {first} from "rxjs/operators";
import {AlertService} from "../../_services";
import {Router} from "@angular/router";

@Component({
    selector: 'app-user-parameters',
    templateUrl: './user-parameters.html',
    styleUrls: ['./user-parameters.css'],
    providers: [UserParametersService]
})

export class UserParametersComponent implements OnInit {

    userParameters: UserParameters;
    restrictions: Restriction[];

    constructor(private userParametersService: UserParametersService,
                private alertService: AlertService,
                private router: Router) {

        this.userParameters = new UserParameters();
    }

    ngOnInit() {

        this.userParametersService.getUserParameters()
            .pipe(first())
            .subscribe(
                (data: UserParameters) => {
                    this.userParameters = data;
                },
                error => {
                    this.alertService.error(error);
                    console.log(error);
                    this.router.navigate(['/login']);
                });


        this.userParametersService.getRestrictions()
            .pipe(first())
            .subscribe(
                (data: Restriction[]) => {
                    this.restrictions = data;
                },
                error => {
                    this.alertService.error(error);
                    console.log(error);
                });

    }

    onSubmit() {

        this.userParametersService.setUserParameters(this.userParameters)
            .subscribe(
                (data: any) => {
                    console.log(data);
                    this.router.navigate(['/welcome/' + this.userParameters.username]);
                },
                error => {
                    console.log(error);
                    // this.allChangeSaved = false;
                });

        /*
                this.userParametersService.setRestrictions(this.restrictions)
                    .subscribe(
                        data => {
                            console.log(data);
                        },
                        error => {
                            console.log(error);
                            this.allChangeSaved = false;
                        });
        */

    }

}
