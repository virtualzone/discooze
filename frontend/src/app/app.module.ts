import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule } from "@angular/forms";
import { HttpModule }    from "@angular/http";
import { RouterModule, Router, NavigationStart, NavigationEnd } from "@angular/router";
import { HttpClientModule, HttpClient } from "@angular/common/http";

import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";

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

export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, "./i18n/", ".json");
}

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        HttpClientModule,
        RouterModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: (createTranslateLoader),
                deps: [HttpClient]
            }
        }),
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
