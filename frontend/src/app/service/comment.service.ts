import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { HttpService } from "./http.service";
import { CrudService } from "./crud.service";
import { SessionService } from "./session.service";
import { Comment } from "../model/comment";
import { Headers } from "@angular/http/src/headers";

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
            .get(this.httpService.getUrl(this.getPath() + "/search/findByPanelId?id=" + panelId), this.httpService.getOptions())
            .toPromise()
            .then(res => {
                let list: Comment[] = (<Comment[]>res.json()._embedded[this.getPath()]).map(o => this.newTypeInstance().deserialize(o));
                resolve(list);
            })
            .catch(error => reject(this.httpService.handleError(error)));
        });
    }

    public publish(panelId: string, text: string): Promise<Comment> {
        let payload: any = {
            panelId: panelId,
            text: text
        };
        return new Promise((resolve, reject) => {
            this.http.post(this.httpService.getUrl(this.getPath() + "/publish"), payload, this.httpService.getOptions())
            .toPromise()
            .then(res => {
                let entity: Comment = new Comment().deserialize(res.json());
                resolve(entity);
            })
            .catch(error => reject(this.httpService.handleError(error)));
        });
    }

    public uploadAttachment(file: File, commentId: string): Promise<string> {
        let formData: FormData = new FormData();
        formData.append('file', file, file.name);
        let headers: Headers = this.httpService.getHeaders();
        headers.delete("Content-Type");
        let options = {headers: headers};
        return new Promise((resolve, reject) => {
            this.http.post(this.httpService.getUrl("attachments/set/" + commentId), formData, options)
            .toPromise()
            .then(res => {
                resolve(res.text());
            })
            .catch(error => reject(this.httpService.handleError(error)));
        });
    }

    getAttachmentUrl(commentId: string): string {
        return this.httpService.getUrl("attachments/get/" + commentId);
    }
}
