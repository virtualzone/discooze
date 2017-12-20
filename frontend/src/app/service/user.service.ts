import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { HttpService } from "./http.service";
import { CrudService } from "./crud.service";
import { SessionService } from "./session.service";
import { User } from "../model/user";

@Injectable()
export class UserService extends CrudService<User> {
    constructor(
        protected httpService: HttpService,
        protected http: Http,
        private sessionService: SessionService
    ) {
        super(httpService, http);
    }

    protected newTypeInstance(): User {
        return new User();
    }

    protected getPath(): string {
        return "users";
    }

    protected getListSortField(): string {
        return "username";
    }

    public getUserByUsername(username: string): Promise<User> {
        return new Promise((resolve, reject) => {
            this.http
            .get(this.httpService.getUrl(this.getPath() + "/search/findByUsername?username=" + username), this.httpService.getOptions())
            .toPromise()
            .then(res => {
                let entity = this.newTypeInstance().deserialize(<User>res.json());
                resolve(entity);
            })
            .catch(error => reject(this.httpService.handleError(error)));
        });
    }

    public getMe(): Promise<User> {
        return new Promise((resolve, reject) => {
            this.http
            .get(this.httpService.getUrl(this.getPath() + "/search/me"), this.httpService.getOptions())
            .toPromise()
            .then(res => {
                let entity = this.newTypeInstance().deserialize(<User>res.json());
                resolve(entity);
            })
            .catch(error => reject(this.httpService.handleError(error)));
        });
    }

    public setPassword(userId: string, password: string): Promise<void> {
        let payload: any = {
            password: password
        };
        return this.http.put(this.httpService.getUrl("users/setPassword/" + userId), payload, this.httpService.getOptions())
                .toPromise()
                .then(res => {
                    return;
                })
                .catch(error => {
                    throw this.httpService.handleError(error);
                });
    }
}
