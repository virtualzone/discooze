package net.weweave.discooze.backend.service;

import net.weweave.discooze.backend.domain.Panel;
import net.weweave.discooze.backend.domain.SystemSetting;
import net.weweave.discooze.backend.domain.SystemSettingKey;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class PanelRepositoryImpl implements PanelRepositoryCustom {
    @Autowired
    private SystemSettingRepository systemSettingRepository;

    @Autowired
    private PanelRepository panelRepository;

    @Override
    public String homePanelId() {
        SystemSetting homePanelSetting = getSystemSettingRepository().findOne(SystemSettingKey.HOME_PANEL);
        if (homePanelSetting != null) {
            return homePanelSetting.getValue();
        }
        return null;
    }

    @Override
    public Panel homePanel() {
        String homePanelId = this.homePanelId();
        if (homePanelId != null && !homePanelId.equals("")) {
            return getPanelRepository().findOne(UUID.fromString(homePanelId));
        }
        return null;
    }

    public SystemSettingRepository getSystemSettingRepository() {
        return systemSettingRepository;
    }

    public void setSystemSettingRepository(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    public PanelRepository getPanelRepository() {
        return panelRepository;
    }

    public void setPanelRepository(PanelRepository panelRepository) {
        this.panelRepository = panelRepository;
    }
}
