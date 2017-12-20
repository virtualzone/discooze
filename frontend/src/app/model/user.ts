import { RestModel } from "./rest-model";

export class User extends RestModel<User> {
    username: string;
    displayName: string;
    roleAdmin: boolean;
    loginType: string;

    serialize(): Object {
        return Object.assign(super.serialize(), {
            "username": this.username,
            "displayName": this.displayName,
            "roleAdmin": this.roleAdmin,
            "loginType": this.loginType
        });
    }

    deserialize(input: any): User {
        this._deserialize(input);
        this.username = input.username;
        this.displayName = input.displayName;
        this.roleAdmin = input.roleAdmin;
        this.loginType = input.loginType;
        return this;
    }
}
