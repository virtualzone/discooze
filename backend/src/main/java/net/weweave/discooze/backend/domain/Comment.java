package net.weweave.discooze.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends AbstractPersistentObject {
    @ManyToOne(optional = false)
    private Panel panel;

    @ManyToOne(optional = false)
    private User author;

    @Column(nullable = false, length = 5000)
    private String text;

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
