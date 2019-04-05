import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';

import {AlertService, AuthenticationService, UserService} from 'src/app/_services';

@Component({
    selector: 'app-recovery',
    templateUrl: './recovery.component.html',
    styleUrls: ['./recovery.component.css']
})
export class RecoveryComponent implements OnInit {

    loginForm: FormGroup;
    passwordForm: FormGroup;
    loading = false;
    submitted = false;
    http: any;
    userData: any;
    isCorrect = true;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private alertService: AlertService
    ) {
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue) {
            this.router.navigate(['/']);
        }
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.loginForm.controls;
    }

    get r() {
        return this.passwordForm.controls;
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            email: ['', [Validators.email, Validators.required]]
        });
        this.passwordForm = this.formBuilder.group({
            secretAnswer: ['', Validators.required],
            password: ['', Validators.required]
        });
    }

    onSubmit() {
        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }
        this.loading = true;
        this.userService.getAnswerByEmail(this.loginForm.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.userData = data;
                    console.log(this.userData);
                    this.alertService.success('Registration successful', true);
                    this.submitted = true;
                    this.loading = false;
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                    this.isCorrect = false;
                });
    }

    onEnter() {
        console.log(this.passwordForm.value);
        this.userService.changePassword(this.passwordForm.value, this.loginForm.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('Change password successful', true);
                    this.router.navigate(['/login']);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}
