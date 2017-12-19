import { RestModel } from "./rest-model";

export class Panel extends RestModel<Panel> {
    title: string;
    url: string;
    headline: string;
    footer: string;
    content: string;
    type: string;

    serialize(): Object {
        return Object.assign(super.serialize(), {
            "title": this.title,
            "url": this.url,
            "headline": this.headline,
            "footer": this.footer,
            "content": this.content,
            "type": this.type
        });
    }

    deserialize(input: any): Panel {
        this._deserialize(input);
        this.title = input.title;
        this.url = input.url;
        this.headline = input.headline;
        this.footer = input.footer;
        this.content = input.content;
        this.type = input.type;
        return this;
    }
}
