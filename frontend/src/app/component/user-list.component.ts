import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute, Params } from "@angular/router";
import { EntityListComponent } from "./entity-list.component";
import { User } from "../model/user";
import { UserService } from "../service/user.service";

@Component({
    templateUrl: "./user-list.component.html",
    providers: [
        UserService
    ]
})
export class UserListComponent extends EntityListComponent<User> {
    constructor(
        protected router: Router,
        protected userService: UserService
    ) {
        super(router, userService);
    }

    protected getEditPath(): string {
        return "/_admin/users/edit";
    }
}
