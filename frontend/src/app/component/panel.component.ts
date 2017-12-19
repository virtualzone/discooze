import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute, Params } from "@angular/router";
import { PanelService } from "../service/panel.service";
import { Panel } from "../model/panel";
import { Comment } from "../model/comment";
import { CommentService } from "../service/comment.service";
import { User } from "../model/user";
import { UserService } from "../service/user.service";

@Component({
    templateUrl: "./panel.component.html",
    providers: [
        PanelService,
        CommentService,
        UserService
    ]
})
export class PanelComponent implements OnInit {
    panel: Panel = null;
    comments: Comment[];
    errorLoading: boolean = false;
    comment: Comment = new Comment();

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private panelService: PanelService,
        private commentService: CommentService,
        private userService: UserService
    ) {
        this.comment.author = new User();
        // TOOD REmove debug code
        this.comment.text = "Das ist ein Test";
        this.comment.author.username = "admin";
    }

    submit(): void {
        // Load user
        this.userService.getUserByUsername(this.comment.author.username)
            .then(user => {
                this.comment.author = user;
                this.commentService.save(this.comment)
                    .then(comment => alert(comment.id))
                    .catch(e => alert("could not save comment"));
            })
            .catch(e => alert("No such user!"));
    }

    ngOnInit(): void {
        this.route.params.forEach((params: Params) => {
            let url: string = "";
            for (let i=0; i<=5; i++) {
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
        this.commentService.getCommentsForPanel(panel.id)
            .then(comments => this.comments = comments);
    }
}
