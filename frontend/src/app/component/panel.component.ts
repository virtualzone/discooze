import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute, Params } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { PanelService } from "../service/panel.service";
import { Panel } from "../model/panel";
import { Comment } from "../model/comment";
import { CommentService } from "../service/comment.service";
import { User } from "../model/user";
import { UserService } from "../service/user.service";
import { SessionService } from "../service/session.service";
import { AuthService } from "../service/auth.service";

@Component({
    templateUrl: "./panel.component.html",
    providers: [
        PanelService,
        CommentService,
        UserService,
        AuthService
    ]
})
export class PanelComponent implements OnInit {
    panel: Panel = null;
    comments: Comment[];
    errorLoading: boolean = false;
    submitting: boolean = false;
    success: boolean = false;
    authError: boolean = false;
    text: string = "";
    username: string = "";
    password: string = "";
    requirePassword: boolean = false;
    file = null;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private panelService: PanelService,
        private commentService: CommentService,
        private userService: UserService,
        private sessionService: SessionService,
        private authService: AuthService,
        private sanitizer: DomSanitizer
    ) {
        // TOOD REmove debug code
        this.text = "Das ist ein Test";
        this.username = "admin";
    }

    submit(): void {
        this.submitting = true;
        if (!this.sessionService.isLoggedIn) {
            this.authService.login(this.username, this.password)
                .then(user => this.publishComment())
                .catch(e => {
                    if (e.status === 400) {
                        this.requirePassword = true;
                        this.submitting = false;
                        setTimeout(() => document.getElementById("password").focus(), 50);
                    } else if (e.status === 401) {
                        this.submitting = false;
                        this.authError = true;
                        setTimeout(() => document.getElementById("password").focus(), 50);
                    } else if (e.status === 404) {
                        this.submitting = false;
                        this.authError = true;
                        setTimeout(() => document.getElementById("username").focus(), 50);
                    }
                });
        } else {
            this.publishComment();
        }
    }

    fileChange(event): void {
        let fileList: FileList = event.target.files;
        if (fileList.length > 0) {
            this.file = fileList[0];
        }
    }

    private publishComment(): void {
        this.commentService.publish(this.panel.id, this.text)
            .then(comment => {
                if (this.file) {
                    this.commentService.uploadAttachment(this.file, comment.id)
                        .then(attachmentId => {
                            this.loadComments();
                            this.success = true;
                        });
                } else {
                    this.loadComments();
                    this.success = true;
                }
            });
    }

    ngOnInit(): void {
        this.route.params.forEach((params: Params) => {
            let url: string = "";
            for (let i = 0; i <= 5; i++) {
                if (params["u" + i]) {
                    url += "/" + params["u" + i];
                }
            }
            if (url) {
                this.panelService.getPanelByUrl(url)
                    .then(panel => this.onPanelLoaded(panel))
                    .catch(e => this.errorLoading = true);
            } else {
                this.panelService.getHomePanel()
                    .then(panel => this.onPanelLoaded(panel))
                    .catch(e => this.errorLoading = true);
            }
        });
    }

    private onPanelLoaded(panel: Panel): void {
        this.panel = panel;
        this.loadComments();
    }

    private loadComments(): void {
        this.commentService.getCommentsForPanel(this.panel.id)
            .then(comments => this.comments = comments);
    }
}
