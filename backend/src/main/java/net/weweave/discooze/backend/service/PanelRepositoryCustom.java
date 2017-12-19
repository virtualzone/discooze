package net.weweave.discooze.backend.service;

import net.weweave.discooze.backend.domain.Panel;

public interface PanelRepositoryCustom {
    String homePanelId();
    Panel homePanel();
}
