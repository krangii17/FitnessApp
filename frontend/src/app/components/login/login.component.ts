import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from 'src/app/_services';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from "rxjs/operators";


@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    error = null;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService) {
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['', [Validators.required, Validators.email]],
            password: ['', Validators.required]
        });
        // reset login status
        this.authenticationService.logout();
        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.loginForm.controls;
    }

    onSubmit() {
        this.submitted = true;
        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }
        this.loading = true;

        this.authenticationService.username = this.f.username.value;
        this.authenticationService.password = this.f.password.value;

        this.authenticationService.login()
            .pipe(first())
            .subscribe(
                (data: any) => {
                    this.authenticationService.userLoggedIn = true;
                    console.log(data);
                    this.router.navigate(['/welcome/' + this.f.username.value]);
                },
                error => {
                    this.authenticationService.userLoggedIn = false;
                    this.error = error.toLocaleString();
                    this.loading = false;
                    console.log(error);
                });
    }
}
