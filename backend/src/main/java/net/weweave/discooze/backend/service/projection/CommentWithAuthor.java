package net.weweave.discooze.backend.service.projection;


import net.weweave.discooze.backend.domain.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineAuthor", types = { User.class })
public interface CommentWithAuthor extends AbstractProjection {
    User getAuthor();
    String getText();
    Boolean getHasAttachment();
}
