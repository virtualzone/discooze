import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { HttpService } from "./http.service";
import { CrudService } from "./crud.service";
import { SessionService } from "./session.service";
import { SystemSetting } from "../model/system-setting";

@Injectable()
export class SystemSettingService {
    constructor(
        protected httpService: HttpService,
        protected http: Http,
        private sessionService: SessionService
    ) {}

    list(): Promise<SystemSetting[]> {
        return new Promise((resolve, reject) => {
            this.http
            .get(this.httpService.getUrl("systemSettings"), this.httpService.getOptions())
            .toPromise()
            .then(res => {
                let list: SystemSetting[] = (<SystemSetting[]>res.json()._embedded["systemSettings"])
                    .map(o => new SystemSetting().deserialize(o));
                resolve(list);
            })
            .catch(error => reject(this.httpService.handleError(error)));
        });
    }

    save(entity: SystemSetting): Promise<SystemSetting> {
        return new Promise((resolve, reject) => {
            this.http.put(this.httpService.getUrl("systemSettings/" + entity.key), entity.serialize(), this.httpService.getOptions())
            .toPromise()
            .then(res => {
                resolve(entity);
            })
            .catch(error => reject(this.httpService.handleError(error)));
        });
    }
}
