import { Injectable } from "@angular/core";
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";

import { SessionService } from "../service/session.service";

@Injectable()
export class AdminGuard implements CanActivate {
    constructor(
        private router: Router,
        private sessionService: SessionService
    ) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        if (!this.sessionService.isLoggedIn || !this.sessionService.user.roleAdmin) {
            this.router.navigate(["/_admin/login"]);
            return false;
        }
        return true;
    }
}
