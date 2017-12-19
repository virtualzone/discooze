import { NgModule, ModuleWithProviders } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { PanelComponent } from "./component/panel.component";
import { PanelListComponent } from "./component/panel-list.component";
import { PanelEditComponent } from "./component/panel-edit.component";
import { LoginComponent } from "./component/login.component";

const appRoutes: Routes = [
    // "Any" routes
    { path: "_admin/login", component: LoginComponent },
    { path: "_admin/panels", component: PanelListComponent },
    { path: "_admin/panels/new", component: PanelEditComponent },
    { path: "_admin/panels/edit/:id", component: PanelEditComponent },
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
