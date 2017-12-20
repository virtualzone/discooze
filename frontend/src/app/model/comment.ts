import { RestModel } from "./rest-model";
import { Panel } from "./panel";
import { User } from "./user";

export class Comment extends RestModel<Comment> {
    panel: Panel;
    author: User;
    text: string;
    hasAttachment: boolean;

    serialize(): Object {
        return Object.assign(super.serialize(), {
            "panel": this.panel.serialize(),
            "author": this.author.serialize(),
            "text": this.text
        });
    }

    deserialize(input: any): Comment {
        this._deserialize(input);
        this.panel = input.panel ? new Panel().deserialize(input.panel) : null;
        this.author = input.author ? new User().deserialize(input.author) : null;
        this.text = input.text;
        this.hasAttachment = input.hasAttachment;
        return this;
    }
}
