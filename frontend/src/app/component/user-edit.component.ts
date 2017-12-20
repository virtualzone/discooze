import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute, Params } from "@angular/router";
import { EntityEditComponent } from "./entity-edit.component";
import { User } from "../model/user";
import { UserService } from "../service/user.service";

@Component({
    templateUrl: "./user-edit.component.html",
    providers: [
        UserService
    ]
})
export class UserEditComponent extends EntityEditComponent<User> {
    changePassword: boolean = true;
    password: string = "";

    constructor(
        protected route: ActivatedRoute,
        protected router: Router,
        protected userService: UserService
    ) {
        super(route, router, userService);
    }

    protected newTypeInstance(): User {
        return new User();
    }

    protected getListPath(): string {
        return "/_admin/users";
    }

    protected onEntityLoaded(): void {
        if (this.entity.id) {
            this.changePassword = false;
        }
    }

    protected onEntitiySaved(): Promise<void> {
        if (this.entity.loginType === "PASSWORD" && this.changePassword) {
            return this.userService.setPassword(this.entity.id, this.password);
        } else {
            return super.onEntitiySaved();
        }
    }
}
