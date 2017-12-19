import { platformBrowserDynamic } from "@angular/platform-browser-dynamic";
import { enableProdMode } from "@angular/core";
import "jquery";

import { AppModule } from "./app.module";

if (location.hostname !== "localhost") {
    enableProdMode();
}
const platform = platformBrowserDynamic();
platform.bootstrapModule(AppModule);
