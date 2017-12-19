import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute, Params } from "@angular/router";
import { PanelService } from "../service/panel.service";
import { EntityListComponent } from "./entity-list.component";
import { Panel } from "../model/panel";

@Component({
    templateUrl: "./panel-list.component.html",
    providers: [
        PanelService
    ]
})
export class PanelListComponent extends EntityListComponent<Panel> {
    constructor(
        protected router: Router,
        protected panelService: PanelService
    ) {
        super(router, panelService);
    }

    protected getEditPath(): string {
        return "/_admin/panels/edit";
    }
}
