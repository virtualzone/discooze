import { NgModule, ModuleWithProviders } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { PanelComponent } from "./component/panel.component";
import { PanelListComponent } from "./component/panel-list.component";
import { PanelEditComponent } from "./component/panel-edit.component";
import { LoginComponent } from "./component/login.component";
import { AdminComponent } from "./component/admin.component";
import { GuestGuard } from "./guard/guest-guard.service";
import { AdminGuard } from "./guard/admin-guard.service";
import { UserListComponent } from "./component/user-list.component";
import { UserEditComponent } from "./component/user-edit.component";
import { SettingsComponent } from "./component/settings.component";

const appRoutes: Routes = [
    { path: "_admin/login", component: LoginComponent, canActivate: [GuestGuard] },

    { path: "_admin", component: AdminComponent, canActivate: [AdminGuard] },
    { path: "_admin/panels", component: PanelListComponent, canActivate: [AdminGuard] },
    { path: "_admin/panels/new", component: PanelEditComponent, canActivate: [AdminGuard] },
    { path: "_admin/panels/edit/:id", component: PanelEditComponent, canActivate: [AdminGuard] },
    { path: "_admin/users", component: UserListComponent, canActivate: [AdminGuard] },
    { path: "_admin/users/new", component: UserEditComponent, canActivate: [AdminGuard] },
    { path: "_admin/users/edit/:id", component: UserEditComponent, canActivate: [AdminGuard] },
    { path: "_admin/settings", component: SettingsComponent, canActivate: [AdminGuard] },

    { path: ":u1", component: PanelComponent },
    { path: ":u1/:u2", component: PanelComponent },
    { path: ":u1/:u2/:u3", component: PanelComponent },
    { path: ":u1/:u2/:u3/:u4", component: PanelComponent },
    { path: ":u1/:u2/:u3/:u4/:u5", component: PanelComponent },
    
    {
        path: "",
        component: PanelComponent,
        pathMatch: "full"
    }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
