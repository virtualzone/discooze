import { RestModel } from "./rest-model";

export class SystemSetting {
    key: string;
    value: string;

    serialize(): Object {
        return {
            "key": this.key,
            "value": this.value
        };
    }

    deserialize(input: any): SystemSetting {
        this.key = RestModel.extractId(input);
        this.value = input.value;
        return this;
    }
}
