import { Component, Input, OnInit } from "@angular/core";
import { Router } from "@angular/router";

import { AuthService } from "../service/auth.service";

@Component({
    templateUrl: "./login.component.html",
    providers: [
        AuthService
    ]
})
export class LoginComponent {
    model: any = {
        username: "",
        password: ""
    };
    loginError: boolean = false;
    submitting: boolean = false;

    constructor(
        private router: Router,
        private authService: AuthService
    ) {}

    submit(): void {
        this.loginError = false;
        this.submitting = true;
        this.authService.login(this.model.username, this.model.password)
            .then(res => {
                this.router.navigate(["/_admin/panels"]);
            })
            .catch(res => {
                this.loginError = true;
                this.submitting = false;
            });
    }
}
