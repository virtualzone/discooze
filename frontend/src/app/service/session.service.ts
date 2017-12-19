import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

import "rxjs/add/operator/toPromise";
import { User } from "../model/user";

@Injectable()
export class SessionService {
    isLoggedIn: boolean = false;
    jwt: string = "";
    user: User = new User();

    constructor(
        private router: Router
    ) {
        let jwt = sessionStorage.getItem("jwt");
        if (jwt) {
            this.jwt = jwt;
            this.isLoggedIn = true;
            let userSerialized = sessionStorage.getItem("user");
            if (userSerialized) {
                this.user = new User().deserialize(JSON.parse(userSerialized));
            }
        }
    }

    saveJwt(jwt: string): void {
        sessionStorage.setItem("jwt", jwt);
        this.jwt = jwt;
        this.isLoggedIn = true;
    }

    saveUser(user: User): void {
        sessionStorage.setItem("user", JSON.stringify(user.serialize()));
        this.user = user;
    }

    logout(): boolean {
        sessionStorage.removeItem("jwt");
        sessionStorage.removeItem("user");
        this.isLoggedIn = false;
        this.jwt = "";
        this.user = new User();
        this.router.navigate(["/panel"]);
        return false;
    }
}
