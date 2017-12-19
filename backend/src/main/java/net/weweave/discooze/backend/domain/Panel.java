package net.weweave.discooze.backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Panel extends AbstractPersistentObject {
    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false, unique = true)
    private String url;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PanelType type;

    private String headline;
    private String footer;

    @NotNull
    @Column(nullable = false, length = 5000)
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PanelType getType() {
        return type;
    }

    public void setType(PanelType type) {
        this.type = type;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
