import { RestModel } from "./rest-model";

export class User extends RestModel<User> {
    username: string;
    displayName: string;

    serialize(): Object {
        return Object.assign(super.serialize(), {
            "username": this.username,
            "displayName": this.displayName
        });
    }

    deserialize(input: any): User {
        this._deserialize(input);
        this.username = input.username;
        this.displayName = input.displayName;
        return this;
    }
}
