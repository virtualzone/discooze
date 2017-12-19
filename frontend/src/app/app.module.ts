import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule } from "@angular/forms";
import { HttpModule }    from "@angular/http";
import { RouterModule, Router, NavigationStart, NavigationEnd } from "@angular/router";

import { routing } from "./app.routing";

import { AppComponent } from "./component/app.component";
import { PanelComponent } from "./component/panel.component";
import { PanelListComponent } from "./component/panel-list.component";
import { PanelEditComponent } from "./component/panel-edit.component";
import { HttpService } from "./service/http.service";
import { SessionService } from "./service/session.service";
import { LoginComponent } from "./component/login.component";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        RouterModule,
        routing
    ],
    declarations: [
        AppComponent,
        LoginComponent,
        PanelComponent,
        PanelListComponent,
        PanelEditComponent
    ],
    providers: [
        HttpService,
        SessionService
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule {
}
