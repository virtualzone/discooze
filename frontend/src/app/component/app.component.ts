import { Component } from "@angular/core";
import { Router, Event as RouterEvent, NavigationEnd } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";

@Component({
    selector: "main.container",
    template: `
        <router-outlet></router-outlet>
    `
})
export class AppComponent {
    constructor(
        private router: Router,
        private translate: TranslateService
    ) {
        router.events.subscribe((event: RouterEvent) => {
            this.navigationInterceptor(event);
        });
        translate.setDefaultLang("en");
        let browserLang: string = translate.getBrowserLang();
        if (browserLang) {
            browserLang = browserLang.toLocaleLowerCase();
            if (browserLang === "de") {
                translate.use("de");
            }
        }
    }

    navigationInterceptor(event: RouterEvent): void {
        if (event instanceof NavigationEnd) {
            //$("#loading-layer").hide();
        }
    }
}
