import { Component, Input, OnInit } from "@angular/core";
import { SystemSettingService } from "../service/system-setting.service";
import { SystemSetting } from "../model/system-setting";
import { Panel } from "../model/panel";
import { PanelService } from "../service/panel.service";

@Component({
    templateUrl: "./settings.component.html",
    providers: [
        SystemSettingService,
        PanelService
    ]
})
export class SettingsComponent implements OnInit {
    submitting: boolean = false;
    success: boolean = false;
    error: boolean = false;
    settings: Object = {};
    panels: Panel[];

    constructor(
        private systemSettingService: SystemSettingService,
        private panelService: PanelService
    ) {}

    ngOnInit(): void {
        this.systemSettingService.list()
            .then(settings => settings.forEach(s => this.settings[s.key] = s.value));
        this.panelService.list().then(panels => this.panels = panels);
    }

    submit(): void {
        this.submitting = true;
        this.success = false;
        let settings: SystemSetting[] = [];
        let promises: Promise<SystemSetting>[] = [];
        for (let key of Object.keys(this.settings)) {
            let value: string = this.settings[key];
            let setting: SystemSetting = new SystemSetting();
            setting.key = key;
            setting.value = value;
            settings.push(setting);
            promises.push(this.systemSettingService.save(setting));
        }
        Promise.all(promises)
            .then(result => {
                this.submitting = false;
                this.success = true;
            }).catch(e => {
                this.submitting = false;
                this.success = false;
                this.error = true;
            });
    }
}
