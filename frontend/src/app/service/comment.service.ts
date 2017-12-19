import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { HttpService } from "./http.service";
import { CrudService } from "./crud.service";
import { SessionService } from "./session.service";
import { Comment } from "../model/comment";

@Injectable()
export class CommentService extends CrudService<Comment> {
    constructor(
        protected httpService: HttpService,
        protected http: Http,
        private sessionService: SessionService
    ) {
        super(httpService, http);
    }

    protected newTypeInstance(): Comment {
        return new Comment();
    }

    protected getPath(): string {
        return "comments";
    }

    protected getListSortField(): string {
        return "created";
    }

    public getCommentsForPanel(panelId: string): Promise<Comment[]> {
        return new Promise((resolve, reject) => {
            this.http
            .get(this.httpService.getUrl(this.getPath() + "/search/findByPanelId?id=" + panelId + "&sort=created"), this.httpService.getOptions())
            .toPromise()
            .then(res => {
                console.log(JSON.stringify(res.json()._embedded));
                let list: Comment[] = (<Comment[]>res.json()._embedded[this.getPath()]).map(o => this.newTypeInstance().deserialize(o));
                resolve(list);
            })
            .catch(error => reject(this.httpService.handleError(error)));
        });
    }
}
