import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { HttpService } from "./http.service";
import { CrudService } from "./crud.service";
import { SessionService } from "./session.service";
import { Panel } from "../model/panel";

@Injectable()
export class PanelService extends CrudService<Panel> {
    constructor(
        protected httpService: HttpService,
        protected http: Http,
        private sessionService: SessionService
    ) {
        super(httpService, http);
    }

    protected newTypeInstance(): Panel {
        return new Panel();
    }

    protected getPath(): string {
        return "panels";
    }

    protected getListSortField(): string {
        return "title";
    }

    public getHomePanel(): Promise<Panel> {
        return new Promise((resolve, reject) => {
            this.http
            .get(this.httpService.getUrl(this.getPath() + "/search/homePanel"), this.httpService.getOptions())
            .toPromise()
            .then(res => {
                let entity = this.newTypeInstance().deserialize(<Panel>res.json());
                resolve(entity);
            })
            .catch(error => reject(this.httpService.handleError(error)));
        });
    }

    public getPanelByUrl(url: string): Promise<Panel> {
        return new Promise((resolve, reject) => {
            this.http
            .get(this.httpService.getUrl(this.getPath() + "/search/findByUrl?url=" + url), this.httpService.getOptions())
            .toPromise()
            .then(res => {
                let entity = this.newTypeInstance().deserialize(<Panel>res.json());
                resolve(entity);
            })
            .catch(error => reject(this.httpService.handleError(error)));
        });
    }
}
