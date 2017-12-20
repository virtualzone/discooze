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
import { UserService } from "./service/user.service";
import { AdminComponent } from "./component/admin.component";
import { GuestGuard } from "./guard/guest-guard.service";
import { AdminGuard } from "./guard/admin-guard.service";
import { UserListComponent } from "./component/user-list.component";
import { UserEditComponent } from "./component/user-edit.component";
import { SettingsComponent } from "./component/settings.component";

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
        AdminComponent,
        LoginComponent,
        PanelComponent,
        PanelListComponent,
        PanelEditComponent,
        UserListComponent,
        UserEditComponent,
        SettingsComponent
    ],
    providers: [
        HttpService,
        SessionService,
        UserService,
        GuestGuard,
        AdminGuard
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule {
}
