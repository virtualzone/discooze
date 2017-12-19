import * as moment from "moment";

export abstract class RestModel<T> {
    id: string;
    created: moment.Moment;
    lastModified: moment.Moment;

    serialize(): Object {
        return {
            "id": this.id
        };
    }

    abstract deserialize(input: any): T;

    protected _deserialize(input: any): void {
        this.id = RestModel.extractId(input);
        this.created = (input.created ? moment(input.created) : null);
        this.lastModified = (input.lastUpdate ? moment(input.lastUpdate) : null);
    }

    public static extractId(input: any): string {
        if (input && input._links && input._links.self) {
            let href: string = input._links.self.href;
            if (href && href.indexOf("/") < href.length) {
                return href.substr(href.lastIndexOf("/")+1);
            }
        }
        return undefined;
    }
}
