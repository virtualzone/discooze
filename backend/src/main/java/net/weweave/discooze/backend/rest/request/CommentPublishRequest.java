package net.weweave.discooze.backend.rest.request;

public class CommentPublishRequest {
    private String panelId;
    private String text;

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
