import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute, Params } from "@angular/router";
import { PanelService } from "../service/panel.service";
import { EntityEditComponent } from "./entity-edit.component";
import { Panel } from "../model/panel";

@Component({
    templateUrl: "./panel-edit.component.html",
    providers: [
        PanelService
    ]
})
export class PanelEditComponent extends EntityEditComponent<Panel> {
    constructor(
        protected route: ActivatedRoute,
        protected router: Router,
        protected panelService: PanelService
    ) {
        super(route, router, panelService);
    }

    protected newTypeInstance(): Panel {
        return new Panel();
    }

    protected getListPath(): string {
        return "/_admin/panels";
    }
}
